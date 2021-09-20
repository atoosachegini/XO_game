import javax.annotation.processing.Messager;
import java.util.*;
import java.lang.*;
import java.util.regex.Pattern;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    static class Player {
        private static ArrayList<Player> players = new ArrayList<>();
        private String name;
        private int wins;
        private int draws;
        private int looses;


        public static ArrayList<Player> getPlayers() {
            return players;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setWins(int wins) {
            this.wins = wins;
        }

        public int getWins() {
            return this.wins;
        }

        public void setLooses(int looses) {
            this.looses = looses;
        }

        public int getLooses() {
            return this.looses;
        }

        public void setDraws(int draws) {
            this.draws = draws;
        }

        public int getDraws() {
            return this.draws;
        }

        public void incrementDraw() {
            this.draws++;
        }

        public void incrementLoose() {
            this.looses++;
        }

        public void incrementWin() {
            this.wins++;
        }

        public static void addPlayers(Player player) {
            Player.getPlayers().add(player);
        }


        public static boolean repeatedPlayer(String name) {
            for (int i = 0; i < Player.players.size(); i++) {
                if (players.get(i).getName().equals(name))
                    return true;
            }
            return false;
        }

        public static Player findPlayer(String name) {
            Player save = new Player();
            for (Player player : Player.getPlayers()) {
                if (player.getName().compareTo(name) == 0)
                    save = player;
            }
            return save;
        }
    }

    static class ScoreBoard {


        public static void showScoreBoard() {
            List<Player> alter = new ArrayList<>();
            alter.addAll(Player.getPlayers());
            Collections.sort(alter, new Comparator<Player>() {
                @Override
                public int compare(Player t1, Player t2) {
                    int win = t2.getWins() - t1.getWins();
                    int loos = t1.getLooses() - t2.getLooses();
                    int draw = t1.getDraws() - t2.getDraws();
                    int name = t1.getName().compareTo(t2.getName());
                    if (win != 0)
                        return win;
                    else if (loos != 0)
                        return loos;
                    else if (draw != 0)
                        return draw;
                    else if (name != 0)
                        return name;
                    return name;
                }
            });


            for (int i = 0; i < alter.size(); i++) {
                System.out.println(alter.get(i).getName() + " " + alter.get(i).getWins() + " " + alter.get(i).getLooses()
                        + " " + alter.get(i).getDraws());
            }
        }

        public static void run() {
            Scanner input = Main.scanner;
            while (true) {
                int flag = 0;
                String vorudi = input.nextLine();
                if (vorudi.trim().compareTo("back") == 0) {
                    flag = 1;
                    return;
                }
                if (vorudi.trim().compareTo("quit") == 0) {
                    flag = 1;
                    System.exit(0);
                } else if (flag == 0)
                    System.out.println("Invalid command");

            }
        }
    }

    static class ResumeMenu {
        private static ArrayList<Game> resume = new ArrayList<>();

        public static boolean isInteger(String str) {
            try {
                Integer.parseInt(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public static boolean findResumeGame(Game game) {
            for (int i = 0; i < ResumeMenu.getResume().size(); i++) {
                if (ResumeMenu.getResume().get(i) == game)
                    return true;
            }
            return false;
        }


        public static ArrayList<Game> getResume() {
            return resume;
        }

        public static void pause(Game game) {

            if (!ResumeMenu.findResumeGame(game)) {
                getResume().add(game);
            }

            if (ResumeMenu.findResumeGame(game)) {
                getResume().remove(game);
                getResume().add(game);

            }

        }

        public static void showResumeMenu() {

            int count = 0;
            for (int i = getResume().size() - 1; i >= 0; i--) {
                count++;
                System.out.println(count + ". " + getResume().get(i).getPlayer1().getName() + " " +
                        getResume().get(i).getPlayer2().getName());

            }
        }

        public static void run() {
            Scanner input = Main.scanner;
            while (true) {
                int flag2 = 0;
                String vorudi = input.nextLine();

                if (ResumeMenu.isInteger(vorudi.trim())) {
                    flag2 = 1;
                    if (Integer.parseInt(vorudi.trim()) < getResume().size() + 1 && Integer.parseInt(vorudi.trim()) >= 1) {
                        getResume().get(getResume().size() - Integer.parseInt(vorudi.trim())).setTable();
                        getResume().get(getResume().size() - Integer.parseInt(vorudi.trim())).drawTable();
                        System.out.println(getResume().get(getResume().size() - Integer.parseInt(vorudi.trim())).whosTurn().getName());
                        getResume().get(getResume().size() - Integer.parseInt(vorudi.trim())).run();
                        return;

                    }
                    if (((Integer.parseInt(vorudi.trim()) > getResume().size() || Integer.parseInt(vorudi.trim()) < 0))&&
                            getResume().size()!=0) {
                        System.out.println("Invalid number");
                    }
                    if (getResume().size()==0) {
                        System.out.println("Invalid command");
                    }

                }
                if (vorudi.trim().compareTo("back") == 0) {
                    flag2 = 1;
                    return;
                } else if (flag2 == 0)
                    System.out.println("Invalid command");

            }
        }
    }

    static class Game {
        private int first = -1;
        private int second = -1;
        private Player player1;
        private Player player2;
        private boolean undo1 = true;
        private boolean undo2 = true;
        private int ID;
        private int lenght;
        private int width;
        int result = -1;
        private static ArrayList<Game> games = new ArrayList<>();
        private ArrayList<ArrayList<Integer>> done = new ArrayList<ArrayList<Integer>>();
        private char[][] XO;


        public void setUndo1(boolean undo1) {
            this.undo1 = undo1;
        }


        public boolean getUndo1() {
            return this.undo1;
        }

        public void setUndo2(boolean undo2) {
            this.undo2 = undo2;
        }

        public boolean getUndo2() {
            return this.undo2;
        }


        public void setResult(int result) {
            this.result = result;
        }

        public int getResult() {
            return result;
        }

        public static ArrayList<Game> getGames() {
            return Game.games;
        }


        public char[][] getXO() {
            return XO;
        }

        public void setPlayer1(Player player1) {
            this.player1 = player1;
        }

        public Player getPlayer1() {
            return this.player1;
        }

        public void setPlayer2(Player player2) {
            this.player2 = player2;
        }

        public Player getPlayer2() {
            return this.player2;
        }

        public void setID(int id) {
            this.ID = id;
        }

        public int getID() {
            return this.ID;
        }

        public void setLenght(int lenght) {
            this.lenght = lenght;
        }

        public int getLenght() {
            return this.lenght;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getWidth() {
            return this.width;
        }

        public static void addNewGame(Game game) {
            games.add(game);
        }

        public ArrayList<ArrayList<Integer>> getDone() {
            return this.done;
        }

        public static boolean findGame(Game game) {
            for (int i = 0; i < Game.getGames().size(); i++) {
                if (Game.getGames().get(i) == game)
                    return true;
            }
            return false;
        }


        public void addToDone(int first, int second) {
            ArrayList<Integer> a1 = new ArrayList<>();
            a1.add(first - 1);
            a1.add(second - 1);
            done.add(a1);
        }

        public void setTable() {

            if (this.getDone().size() == 0) {
                for (int i = 0; i < this.getLenght(); i++) {
                    for (int j = 0; j < this.getWidth(); j++) {
                        this.getXO()[i][j] = '_';
                    }
                }
            } else if ((this.getDone().get(this.getDone().size() - 1).get(0) == -1) &&
                    (this.getDone().get(this.getDone().size() - 1).get(1) == -1)) {
                this.getXO()[first][second] = '_';
                this.getDone().remove(this.getDone().size() - 1);
            } else if (this.getDone().size() % 2 == 1) {
                if ((this.getDone().get(this.getDone().size() - 1).get(0) != -1) &&
                        (this.getDone().get(this.getDone().size() - 1).get(1) != -1)) {
                    this.getXO()[this.getDone().get(this.getDone().size() - 1).get(0)]
                            [this.getDone().get(this.getDone().size() - 1).get(1)] = 'X';
                }
            } else if (this.getDone().size() % 2 == 0) {
                if ((this.getDone().get(this.getDone().size() - 1).get(0) != -1) &&
                        (this.getDone().get(this.getDone().size() - 1).get(1) != -1)) {
                    this.getXO()[this.getDone().get(this.getDone().size() - 1).get(0)]
                            [this.getDone().get(this.getDone().size() - 1).get(1)] = 'O';
                }
            }


        }

        public void drawTable() {
            for (int i = 0; i < this.getLenght(); i++) {
                for (int j = 0; j < this.getWidth() - 1; j++) {
                    System.out.print(this.getXO()[i][j]);
                    System.out.print("|");
                }
                System.out.print(this.getXO()[i][this.getWidth() - 1]);
                System.out.println();
            }
        }

        public static void deleteGame(Game game) {
            Game.getGames().remove(game);
        }

        public boolean logic() {
            if (lenght == 3 || width == 3) {
                for (int i = 0; i < this.getLenght(); i++) {
                    for (int j = 0; j < this.getWidth() - 2; j++) {
                        if (this.getXO()[i][j] == 'X' && this.getXO()[i][j + 1] == 'X' && this.getXO()[i][j + 2] == 'X') {
                            this.setResult(1);
                            player1.incrementWin();
                            player2.incrementLoose();
                            return true;
                        }

                        if (this.getXO()[i][j] == 'O' && this.getXO()[i][j + 1] == 'O' && this.getXO()[i][j + 2] == 'O') {
                            this.setResult(2);
                            player2.incrementWin();
                            player1.incrementLoose();
                            return true;
                        }
                    }
                }
                for (int i = 0; i < this.getWidth(); i++) {
                    for (int j = 0; j < this.getLenght() - 2; j++) {
                        if (this.getXO()[j][i] == 'X' && this.getXO()[j + 1][i] == 'X' && this.getXO()[j + 2][i] == 'X') {
                            this.setResult(1);
                            player1.incrementWin();
                            player2.incrementLoose();
                            return true;

                        }
                        if (this.getXO()[j][i] == 'O' && this.getXO()[j + 1][i] == 'O' && this.getXO()[j + 2][i] == 'O') {
                            this.setResult(2);
                            player2.incrementWin();
                            player1.incrementLoose();
                            return true;
                        }
                    }
                }
                for (int i = 0; i < this.getLenght() - 2; i++) {
                    for (int j = 0; j < this.getWidth() - 2; j++) {
                        if (this.getXO()[i][j] == 'X' && this.getXO()[i + 1][j + 1] == 'X' && this.getXO()[i + 2][j + 2] == 'X') {
                            this.setResult(1);
                            player1.incrementWin();
                            player2.incrementLoose();
                            return true;
                        }
                        if (this.getXO()[i][j] == 'O' && this.getXO()[i + 1][j + 1] == 'O' && this.getXO()[i + 2][j + 2] == 'O') {
                            this.setResult(2);
                            player2.incrementWin();
                            player1.incrementLoose();
                            return true;
                        }
                    }
                }
                for (int i = 0; i < this.getLenght() - 2; i++) {
                    for (int j = this.getWidth() - 1; j > 1; j--) {
                        if (this.getXO()[i][j] == 'X' && this.getXO()[i + 1][j - 1] == 'X' &&
                                this.getXO()[i + 2][j - 2] == 'X') {
                            this.setResult(1);
                            player1.incrementWin();
                            player2.incrementLoose();
                            return true;
                        }
                        if (this.getXO()[i][j] == 'O' && this.getXO()[i + 1][j - 1] == 'O' &&
                                this.getXO()[i + 2][j - 2] == 'O') {
                            this.setResult(2);
                            player2.incrementWin();
                            player1.incrementLoose();
                            return true;
                        }
                    }
                }
            } else {
                for (int i = 0; i < this.getLenght(); i++) {
                    for (int j = 0; j < this.getWidth() - 3; j++) {
                        if (this.getXO()[i][j] == 'X' && this.getXO()[i][j + 1] == 'X' &&
                                this.getXO()[i][j + 2] == 'X' && this.getXO()[i][j + 3] == 'X') {
                            this.setResult(1);
                            player1.incrementWin();
                            player2.incrementLoose();
                            return true;
                        }

                        if (this.getXO()[i][j] == 'O' && this.getXO()[i][j + 1] == 'O' &&
                                this.getXO()[i][j + 2] == 'O' && this.getXO()[i][j + 3] == 'O') {
                            this.setResult(2);
                            player2.incrementWin();
                            player1.incrementLoose();
                            return true;
                        }
                    }
                }

                for (int i = 0; i < this.getWidth(); i++) {
                    for (int j = 0; j < this.getLenght() - 3; j++) {
                        if (this.getXO()[j][i] == 'X' && this.getXO()[j + 1][i] == 'X' &&
                                this.getXO()[j + 2][i] == 'X' && this.getXO()[j + 3][i] == 'X') {
                            this.setResult(1);
                            player1.incrementWin();
                            player2.incrementLoose();
                            return true;

                        }

                        if (this.getXO()[j][i] == 'O' && this.getXO()[j + 1][i] == 'O' &&
                                this.getXO()[j + 2][i] == 'O' && this.getXO()[j + 3][i] == 'O') {
                            this.setResult(2);
                            player2.incrementWin();
                            player1.incrementLoose();
                            return true;
                        }
                    }
                }

                for (int i = 0; i < this.getLenght() - 3; i++) {
                    for (int j = 0; j < this.getWidth() - 3; j++) {
                        if (this.getXO()[i][j] == 'X' && this.getXO()[i + 1][j + 1] == 'X' &&
                                this.getXO()[i + 2][j + 2] == 'X' && this.getXO()[i + 3][j + 3] == 'X') {
                            this.setResult(1);
                            player1.incrementWin();
                            player2.incrementLoose();
                            return true;
                        }
                        if (this.getXO()[i][j] == 'O' && this.getXO()[i + 1][j + 1] == 'O' &&
                                this.getXO()[i + 2][j + 2] == 'O' && this.getXO()[i + 3][j + 3] == 'O') {
                            this.setResult(2);
                            player2.incrementWin();
                            player1.incrementLoose();
                            return true;
                        }
                    }
                }
                for (int i = 0; i < this.getLenght() - 3; i++) {
                    for (int j = this.getWidth() - 1; j > 2; j--) {
                        if (this.getXO()[i][j] == 'X' && this.getXO()[i + 1][j - 1] == 'X' &&
                                this.getXO()[i + 2][j - 2] == 'X' && this.getXO()[i + 3][j - 3] == 'X') {
                            this.setResult(1);
                            player1.incrementWin();
                            player2.incrementLoose();
                            return true;
                        }
                        if (this.getXO()[i][j] == 'O' && this.getXO()[i + 1][j - 1] == 'O' &&
                                this.getXO()[i + 2][j - 2] == 'O' && this.getXO()[i + 3][j - 3] == 'O') {
                            this.setResult(2);
                            player2.incrementWin();
                            player1.incrementLoose();
                            return true;
                        }
                    }
                }

            }
            return false;
        }

        public boolean checkIfTheGapsFinished() {
            for (int i = 0; i < this.getLenght(); i++) {
                for (int j = 0; j < this.getWidth(); j++) {
                    if (this.getXO()[i][j] == '_')
                        return false;
                }
            }
            return true;
        }

        public void checkDraw() {
            if (this.checkIfTheGapsFinished()) {
                player1.incrementDraw();
                player2.incrementDraw();
                this.setResult(0);
            }
        }

        public Player whosTurn() {
            if (this.getDone().size() % 2 == 0)
                return player1;
            else
                return player2;
        }

        public void usingUndo() {
            if (this.getDone().size() % 2 == 0) {
                if (this.getUndo2()) {
                    first = this.getDone().get(this.getDone().size() - 1).get(0);
                    second = this.getDone().get(this.getDone().size() - 1).get(1);
                    this.getDone().remove(this.getDone().size() - 1);
                    ArrayList<Integer> a1 = new ArrayList<>();
                    a1.add(-1);
                    a1.add(-1);
                    this.getDone().add(a1);
                    this.setUndo2(false);
                }

            }
            if (this.getDone().size() % 2 == 1) {
                if (this.getUndo1()) {
                    first = this.getDone().get(this.getDone().size() - 1).get(0);
                    second = this.getDone().get(this.getDone().size() - 1).get(1);
                    this.getDone().remove(this.getDone().size() - 1);
                    ArrayList<Integer> a1 = new ArrayList<>();
                    a1.add(-1);
                    a1.add(-1);
                    this.getDone().add(a1);
                    this.setUndo1(false);
                }

            }
        }

        public boolean SearchInDone(int first, int second) {
            for (int i = 0; i < this.getDone().size(); i++) {
                if (this.getDone().get(i).get(0) == first && this.getDone().get(i).get(1) == second) {
                    return true;
                }
            }
            return false;
        }


        public void run() {
            Scanner input = Main.scanner;
            int flag = 0;

            while (true) {
                int flag2 = 0;
                int save = 0;
                String vorudi = input.nextLine();
                if (vorudi.trim().length()>=3&&vorudi.trim().substring(0, 3).contains("put") && vorudi.trim().charAt(3) == '(' && vorudi.trim().charAt(vorudi.trim().length() - 1) == ')') {
                    String[] parts1 = vorudi.trim().substring(4, vorudi.trim().length() - 1).split("\\,");

                    if (0 < Integer.parseInt(parts1[0]) && Integer.parseInt(parts1[0]) <= this.getLenght() &&
                            (0 < Integer.parseInt(parts1[1]) && Integer.parseInt(parts1[1]) <= this.getWidth())) {
                        if (!this.SearchInDone(Integer.parseInt(parts1[0]) - 1, Integer.parseInt(parts1[1]) - 1)) {
                            flag++;
                            flag2 = 1;
                            save = 1;
                            this.addToDone(Integer.parseInt(parts1[0]), Integer.parseInt(parts1[1]));
                            this.setTable();
                            if (this.logic()) {
                                if (this.getResult() == 0)
                                    System.out.println("Draw");
                                if (this.getResult() == 1)
                                    System.out.println("Player" + " " + this.getPlayer1().getName() + " " + "won");
                                if (this.getResult() == 2)
                                    System.out.println("Player" + " " + this.getPlayer2().getName() + " " + "won");
                                ResumeMenu.getResume().remove(this);
                                return;
                            }
                            if (!this.logic() && this.checkIfTheGapsFinished()) {
                                this.checkDraw();
                                System.out.println("Draw");
                                ResumeMenu.getResume().remove(this);
                                return;
                            } else if (!this.logic()) {
                                this.drawTable();
                                System.out.println(this.whosTurn().getName());
                            }

                        }
                    }

                    if (0 >= Integer.parseInt(parts1[0]) || Integer.parseInt(parts1[0]) > this.getLenght() ||
                            (0 >= Integer.parseInt(parts1[1]) || Integer.parseInt(parts1[1]) > this.getWidth())
                    ) {
                        flag2 = 1;
                        System.out.println("Invalid coordination");
                        this.setTable();
                        this.drawTable();
                        System.out.println(this.whosTurn().getName());

                    }
                    if (save == 0 && this.SearchInDone(Integer.parseInt(parts1[0]) - 1, Integer.parseInt(parts1[1]) - 1)) {

                        flag2 = 1;
                        System.out.println("Invalid coordination");
                        this.setTable();
                        this.drawTable();
                        System.out.println(this.whosTurn().getName());
                    }
                }

                if (vorudi.trim().compareTo("pause") == 0) {
                    flag2 = 1;
                    ResumeMenu.pause(this);
                    return;
                }
                if (vorudi.trim().compareTo("stop") == 0) {
                    flag2 = 1;
                    ResumeMenu.getResume().remove(this);
                    Game.deleteGame(this);
                    return;

                }
                if (vorudi.trim().compareTo("undo") == 0) {

                    if (this.getDone().size() < 2) {
                        flag2 = 1;
                        System.out.println("Invalid undo");
                        this.setTable();
                        this.drawTable();
                        System.out.println(this.whosTurn().getName());
                    } else if (this.getDone().size() % 2 == 0 && !this.getUndo2()) {
                        flag2 = 1;
                        System.out.println("Invalid undo");
                        this.setTable();
                        this.drawTable();
                        System.out.println(this.whosTurn().getName());
                    } else if (this.getDone().size() % 2 == 1 && !this.getUndo1()) {
                        flag2 = 1;
                        System.out.println("Invalid undo");
                        this.setTable();
                        this.drawTable();
                        System.out.println(this.whosTurn().getName());
                    } else if (this.getDone().size() >= 2) {
                        flag2 = 1;
                        this.usingUndo();
                        this.setTable();
                        this.drawTable();
                        System.out.println(this.whosTurn().getName());
                    }


                }
                if (flag2 == 0) {
                    System.out.println("Invalid command");
                    this.setTable();
                    this.drawTable();
                    System.out.println(this.whosTurn().getName());

                }

            }

        }
    }

    public static void main(String[] args) {
        Scanner input = Main.scanner;
        int lenght = 3;

        int width = 3;

        while (true) {
            String vorudi = input.nextLine();
            int flag = 0;
            if (vorudi.trim().compareTo("resume") == 0) {
                flag = 1;
                ResumeMenu.showResumeMenu();
                ResumeMenu.run();
            } else if (vorudi.trim().compareTo("new game") == 0) {
                flag = 1;
                System.out.println("Invalid players");
            } else if (vorudi.trim().length() >= 8 && vorudi.trim().substring(0, 8).contains("new game")) {


                String[] parts = vorudi.trim().substring(9).split("\\s");

                if (parts.length == 2) {
                    flag = 1;
                    Game game = new Game();
                    Game.addNewGame(game);
                    game.setLenght(lenght);
                    game.setWidth(width);
                    game.XO = new char[game.getLenght()][game.getWidth()];

                    if (!Player.repeatedPlayer(parts[0])) {
                        Player player1 = new Player();
                        game.setPlayer1(player1);
                        Player.addPlayers(player1);
                        game.getPlayer1().setName(parts[0]);

                        game.getPlayer1().setDraws(0);
                        game.getPlayer1().setLooses(0);
                        game.getPlayer1().setWins(0);
                    } else {
                        game.setPlayer1(Player.findPlayer(parts[0]));
                    }
                    if (!Player.repeatedPlayer(parts[1])) {
                        Player player2 = new Player();
                        game.setPlayer2(player2);
                        Player.addPlayers(player2);
                        game.getPlayer2().setName(parts[1]);

                        game.getPlayer2().setDraws(0);
                        game.getPlayer2().setLooses(0);
                        game.getPlayer2().setWins(0);
                    } else {
                        game.setPlayer2(Player.findPlayer(parts[1]));
                    }

                    game.setTable();
                    game.drawTable();
                    System.out.println(game.whosTurn().getName());

                    game.run();
                }
                if (parts.length == 1) {
                    flag = 1;
                    System.out.println("Invalid players");
                }
                if (parts.length >= 3) {
                    flag = 1;
                    System.out.println("Invalid command");
                }
                flag = 1;
            } else if (vorudi.trim().length() >= 9 && vorudi.trim().substring(0, 9).contains("set table")) {
                if (vorudi.trim().compareTo("set table") == 0) {
                    flag = 1;
                    lenght = 3;
                    width = 3;
                } else if (vorudi.trim().length() > 9 && vorudi.trim().substring(10).matches("\\d+\\*\\d+")) {
                    flag = 1;
                    String[] parts = vorudi.trim().substring(10).split("\\*");
                    lenght = Integer.parseInt(parts[0]);
                    width = Integer.parseInt(parts[1]);
                }
            } else if (vorudi.trim().compareTo("scoreboard") == 0) {
                flag = 1;
                ScoreBoard.showScoreBoard();
                ScoreBoard.run();
            } else if (vorudi.trim().compareTo("quit") == 0) {
                flag = 1;
                System.exit(0);
            }
            if (flag == 0)
                System.out.println("Invalid command");


        }
    }
}