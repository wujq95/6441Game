package service;

public abstract class SaveLoadGame {

    public static MapEditorService mapEditorService;

    public static GamePlayerService gamePlayerService;

    public static ReinforceService reinforceService;

    public static FortifyService fortifyService;

    public static AttackService attackService;

    public static CardService cardService;

    public SaveLoadGame() {
        mapEditorService = new MapEditorService();
        gamePlayerService = new GamePlayerService();
    }

    abstract String saveGame(String fileName);

    abstract  String loadGame(String fileName);
}
