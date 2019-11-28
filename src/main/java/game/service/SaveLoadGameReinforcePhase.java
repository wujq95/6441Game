package service;

import com.sun.deploy.util.StringUtils;
import javafx.scene.paint.Color;
import model.*;

import java.io.*;
import java.util.*;

import static service.MapEditorService.*;

/**
 * Save game during attack phase
 */
public class SaveLoadGameReinforcePhase extends SaveLoadGame {
    /**
     * Save game
     *
     * @param fileName file name
     * @return message
     */
    @Override
    public String saveGame(String fileName) {
        fileName = fileName.trim();
        if (!validateMap()) {
            return "the map is not valid";
        }

        String returnMsg = "";

        List<String> lines = new LinkedList<>();

        String[] filepaths = fileName.split("/");
        String mapName = "name " + filepaths[filepaths.length - 1] + " Map";
        lines.add(mapName);

        String continents = "\n[continents]";
        lines.add(continents);

        List<Continent> updatedContinentList = new LinkedList<>();
        //continentindex for continent id
        int continentIndex = 1;
        for (Continent continent : MapEditorService.mapGraph.getContinentList()) {
            String continentDesc = continent.getContinentName() + " " + continent.getArmyValue() + " " + continent.getColor();
            lines.add(continentDesc);

            continent.setId(continentIndex);
            updatedContinentList.add(continent);
            continentIndex++;
        }

        lines.add("\n[countries]");
        int index = 0;
        for (Country country : MapEditorService.mapGraph.getCountryList()) {
            String countryDesc = "";

            //index for the countries id
            index++;
            if (country.getId() == null) {
                countryDesc = index + " " + country.getCountryName() + " "
                        + findContinentIdByName(country.getParentContinent().getContinentName(), updatedContinentList) + " " + country.getX() + " " + country.getY();
            } else {
                countryDesc = country.getId() + " " + country.getCountryName() + " "
                        + findContinentIdByName(country.getParentContinent().getContinentName(), updatedContinentList) + " " + country.getX() + " " + country.getY();
            }

            lines.add(countryDesc);
        }

        lines.add("\n[borders]");
        for (Map.Entry<Country, Set<Country>> entry : MapEditorService.mapGraph.getAdjacentCountries().entrySet()) {
            StringBuilder borderDesc = new StringBuilder();
            borderDesc.append(entry.getKey().getId());

            for (Country neighbour : entry.getValue()) {
                borderDesc.append(" ").append(neighbour.getId());
            }

            lines.add(borderDesc.toString());
        }

        lines.add("\n[phase]");
        lines.add(String.valueOf(GamePlayerService.checkPhase));

        lines.add("\n[players]");

        for (GamePlayer player : GamePlayerService.playerList) {
            lines.add("\n[playername]");
            lines.add(player.getPlayerName());
            lines.add("\n[countryname]");
            lines.add(StringUtils.join(player.getCountryNameList(), ","));
            lines.add("\n[armyvalue]");
            lines.add(player.getArmyValue().toString());
            lines.add("\n[controlledcontinent]");
            lines.add(StringUtils.join(player.getControlledContinent(), ","));

            lines.add("\n[strategyname]");
            lines.add(player.getStrategyName());
            lines.add("\n[cardlist]");
            lines.add(StringUtils.join(getCardStringList(player.getCardList()), ","));
        }
        lines.add(GamePlayerService.choosePlayer.toString());

        lines.add("\n[cards]");
        lines.add(StringUtils.join(getCardStringList(CardService.cardDeckList), ","));
        lines.add(String.valueOf(CardService.notExchangeCards));
        File mapFile = new File(fileName);

        lines.add("\n[attack]");
        lines.add(AttackService.fromCountry);
        lines.add(AttackService.toCountry);
        lines.add(AttackService.fromDiceNum.toString());
        lines.add(AttackService.toDiceNum.toString());
        lines.add(StringUtils.join(convertIntegerListToStringList(AttackService.fromDiceResultList), ","));
        lines.add(StringUtils.join(convertIntegerListToStringList(AttackService.toDiceResultList), ","));
        lines.add(String.valueOf(AttackService.conqueredAll));
        lines.add(String.valueOf(AttackService.ConqueredAtleastOneIntheturn));

        lines.add("\n[cardrewarded]");

        for (Map.Entry<GamePlayer, Card> playercard : CardService.lastRewardedCard.entrySet()) {
            lines.add(playercard.getKey().getPlayerName() + "," + playercard.getValue());
        }

        lines.add("\n[cardlistrewarded]");

        for (Map.Entry<GamePlayer, List<Card>> playercard : CardService.rewardedCardsAfterDefeatAnotherPlayer.entrySet()) {
            lines.add(playercard.getKey().getPlayerName() + "," + StringUtils.join(getCardStringList(playercard.getValue()), ","));
        }

        if (mapFile.isFile()) {
            mapFile.delete();
            File newFile = new File(fileName);

            try {
                FileWriter fileWriter = new FileWriter(newFile, false);
                for (String str : lines) {
                    fileWriter.write(str + System.lineSeparator());
                }
                fileWriter.close();

                returnMsg = "saveGame success";
            } catch (IOException e) {
                returnMsg = "saveGame failed";
                return returnMsg;
            }
        } else {
            File newFile = new File(fileName);

            try {
                FileWriter fileWriter = new FileWriter(newFile, false);
                for (String str : lines) {
                    fileWriter.write(str + System.lineSeparator());
                }
                fileWriter.close();

                returnMsg = "saveGame success";
            } catch (IOException e) {
                returnMsg = "saveGame failed";
                return returnMsg;
            }
        }

        return returnMsg;
    }

    /**
     * Load game
     *
     * @param fileName file name
     * @return message
     */
    @Override
    public String loadGame(String fileName) {
        if (fileName.endsWith("\n")) {
            fileName = fileName.trim();
        }
        String returnMsg = "";
        File mapFile = new File(fileName);

        //if the map file exists
        if (mapFile.isFile()) {
            //SET CONTINENT NAMES/COUNTRY NAMES
            HashMap<Integer, Continent> continentMap = new HashMap<>();
            HashMap<Integer, Country> countryHashMap = new HashMap<>();

            List<Country> countryList = new LinkedList<>();
            LinkedHashMap<Country, Set<Country>> adjacentCountries = new LinkedHashMap<>();
            List<Connection> connectionList = new LinkedList<>();

            try {
                BufferedReader br = new BufferedReader(new FileReader(mapFile));
                String line = "";
                while ((line = br.readLine()) != null) {
                    if (line.contains("continents")) {
                        String continentLine = "";
                        int continentIndex = 1;
                        while (!(continentLine = br.readLine()).equals("")) {
                            String[] continentInfos = continentLine.split(" ");
                            int armyValue = Integer.parseInt(continentInfos[1]);
                            Continent continent = new Continent(continentIndex, continentInfos[0], armyValue, Color.web(continentInfos[2]), new LinkedList<Country>());
                            continentMap.put(continentIndex, continent);
                            continentIndex++;
                        }
                    }

                    if (line.contains("countries")) {
                        String countryLine = "";
                        while (!(countryLine = br.readLine()).equals("")) {
                            String[] countryInfos = countryLine.split(" ");
                            int countryId = Integer.parseInt(countryInfos[0]);
                            int parentContinentId = Integer.parseInt(countryInfos[2]);
                            double positionX = Double.parseDouble(countryInfos[3]);
                            double positionY = Double.parseDouble(countryInfos[4]);
                            Country country = new Country(countryId, countryInfos[1], continentMap.get(parentContinentId), positionX, positionY, 0);

                            Continent parent = continentMap.get(parentContinentId);
                            List<Country> continentCountries = parent.getCountries();
                            continentCountries.add(country);
                            parent.setCountries(continentCountries);
                            continentMap.put(parentContinentId, parent);

                            mapGraph.setContinentList(new LinkedList<>(continentMap.values()));

                            countryList.add(country);
                            mapGraph.setCountryList(countryList);
                            countryHashMap.put(countryId, country);
                        }
                    }

                    if (line.contains("borders")) {
                        String borderLine = "";
                        borderLine = br.readLine();
                        while (borderLine != null && !("".equals(borderLine))) {
                            String[] borderInfos = borderLine.split(" ");
                            int countryId = Integer.parseInt(borderInfos[0]);
                            Country country = countryHashMap.get(countryId);

                            //List<Connection> connectionList=new LinkedList<>();
                            Set<Country> neighbourList = new HashSet<>();
                            for (int i = 1; i < borderInfos.length; i++) {
                                Integer neighbourId = Integer.valueOf(borderInfos[i]);

                                Connection connection = new Connection(findCountryByName(country.getCountryName()), findCountryByName(countryHashMap.get(neighbourId).getCountryName()));
                                connection.setCountry1(country);
                                connection.setCountry2(countryHashMap.get(neighbourId));
                                connectionList.add(connection);

                                neighbourList.add(countryHashMap.get(neighbourId));
                            }
                            country.setNeighbours(neighbourList);
                            adjacentCountries.put(country, neighbourList);

                            borderLine = br.readLine();
                        }
                    }

                    if (line.contains("phase")) {
                        String phaseLine = "";
                        try {
                            phaseLine = br.readLine();
                            GamePlayerService.checkPhase = Integer.parseInt(phaseLine);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    if (line.contains("players")) {
                        String playersLine = "";
                        try {
                            playersLine = br.readLine();
                            while (!(playersLine = br.readLine()).equals("")) {
                                GamePlayer player = new GamePlayer();
                                if (line.contains("playername")) {
                                    player.setPlayerName(br.readLine());
                                }
                                if (line.contains("countryname")) {
                                    player.setCountryList(findCountryNames(br.readLine().split(",")));
                                }
                                if (line.contains("armyvalue")) {
                                    player.setArmyValue(Integer.parseInt(br.readLine()));
                                }
                                if (line.contains("controlcontinent")) {
                                    player.setControlledContinent(Arrays.asList(br.readLine().split(",")));
                                }
                                if (line.contains("strategyname")) {
                                    player.setStrategy(findStrategyByName(br.readLine()));
                                }
                                if (line.contains("cardlist")) {
                                    player.setCardStringList(Arrays.asList(br.readLine().split(",")));
                                    GamePlayerService.choosePlayer = Integer.parseInt(br.readLine());
                                }

                                GamePlayerService.playerList.add(player);
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    if (line.contains("cards")) {
                        String cardLine = "";
                        try {
                            cardLine = br.readLine();
                            CardService.cardDeckList = StringArrayToCardList(cardLine.split(","));
                            cardLine = br.readLine();
                            CardService.notExchangeCards = Boolean.parseBoolean(cardLine);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    if (line.contains("attack")) {
                        String cardLine = "";
                        try {
                            cardLine = br.readLine();
                            AttackService.fromCountry = cardLine;
                            cardLine = br.readLine();

                            AttackService.toCountry = cardLine;
                            cardLine = br.readLine();
                            AttackService.fromDiceNum = Integer.valueOf(cardLine);
                            cardLine = br.readLine();
                            AttackService.toDiceNum = Integer.valueOf(cardLine);
                            cardLine = br.readLine();
                            AttackService.fromDiceResultList = StringArrayToIntList(cardLine.split(","));
                            cardLine = br.readLine();
                            AttackService.toDiceResultList = StringArrayToIntList(cardLine.split(","));
                            cardLine = br.readLine();
                            AttackService.conqueredAll = Boolean.parseBoolean(cardLine);
                            cardLine = br.readLine();
                            AttackService.ConqueredAtleastOneIntheturn = Boolean.parseBoolean(cardLine);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    if (line.contains("cardrewarded")) {
                        String rewardLine = "";
                        while (!(rewardLine = br.readLine()).equals("")) {
                            HashMap<GamePlayer, Card> hashMap1 = new HashMap();
                            hashMap1.put(GamePlayerService.findPlayerFromName(rewardLine.split(",")[0]), Card.valueOf(rewardLine.split(",")[1]));
                            CardService.lastRewardedCard = hashMap1;
                        }
                    }

                    if (line.contains("cardlistrewarded")) {
                        String cardLine = "";
                        cardLine = br.readLine();
                        while (cardLine != null && !cardLine.equals("")) {
                            HashMap<GamePlayer, List<Card>> hashMap2 = new HashMap();
                            hashMap2.put(GamePlayerService.findPlayerFromName(cardLine.split(",")[0]), (StringArrayToCardList(cardLine.split(","))));
                            CardService.rewardedCardsAfterDefeatAnotherPlayer = hashMap2;

                            cardLine = br.readLine();
                        }
                    }

                }


            } catch (IOException e) {
                returnMsg = e.getMessage();
                return returnMsg;
            }

            mapGraph.setConnectionList(connectionList);
            mapGraph.setAdjacentCountries(adjacentCountries);

            if (!validateMap()) {
                return "the map is not valid";
            }


            returnMsg = "load game from file " + mapFile + " success";
        } else {
            File file = new File(fileName);
            try {
                //create an empty file
                boolean fileCreated = file.createNewFile();
                if (fileCreated) {
                    returnMsg = "create new file success";
                } else {
                    returnMsg = "create new file fail!";
                }
            } catch (IOException e) {
                returnMsg = e.getMessage();
                return returnMsg;
            }
        }

        return returnMsg;
    }
}