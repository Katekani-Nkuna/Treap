/* Hi all sorry that it took so long but here is a main for the assignment it tests most edge cases and tells you where it gets stuff wrong if you did the first part is the out that we were given the rest is mine
 * For a lot of things there is red output but you should only be concerned if the red output is a sentence
 *
 * All you have to do is replace the main that you were given with this one this doesnt use any functions that you have to add to any of your files
 *
 * Once again this took a while which is why its so late but I would still appreciate donations although they aren't necessary
 *
 * My paypal is:
 * https://www.paypal.me/ChuufMaster
 *
 * Hopefully this helps good luck to everyone
 */

public class Main {

    public static void main(String[] args) {
        task1();
        task2();
    }

    public static void task1() {
        /*
         * You are not given a Main for this task, because we want you to figure out how
         * to do it for yourself.
         *
         * You are provided with a validTreap() function which will print out valid or
         * invalid for a passed in Treap.
         *
         * Use this function to make sure that your heaps follow the rules set by the
         * Assignment.
         *
         * Tip : Create a Main that inserts / deletes a lot of elements and call
         * validTreap after every step
         */
    }

    public static void task2() {
        /*
         * Note that we also want you to create your own main for this task.
         *
         * It takes a while to set the DB up, so an example is given below, feel free to
         * change it to test the rest of the functions
         */
        String[] columns = { "Module Code", "Description", "Credits", "Year", "Session" };
        Database db = new Database(columns, 100);

        String[][] modules = {
                { "LST110", "Language and study skills 110", "6", "1", "Sem 1" },
                { "WTW124", "Mathematics 124", "16", "1", "Sem 2" },
                { "UP0102", "Academic orientation 102", "0", "1", "Year" },
                { "WTW114", "Calculus 114", "16", "1", "Sem 1" },
                { "WTW123", "Numerical analysis 123", "8", "1", "Sem 2" },
                { "PHY114", "First course in physics 114", "16", "1", "Sem 1" },
                { "PHY124", "First course in physics 124", "16", "1", "Sem 2" },
                { "AIM102", "Academic information management 102", "6", "1", "Sem 2" },
                { "COS122", "Operating systems 122", "16", "1", "Sem 2" },
                { "COS132", "Imperative programming 132", "16", "1", "Sem 1" },
                { "COS110", "Program design: Introduction 110", "16", "1", "Sem 2" },
                { "COS151", "Introduction to computer science 151", "8", "1", "Sem 1" },
                { "COS212", "Data structures and algorithms 212", "16", "2", "Sem 1" },
                { "COS226", "Concurrent systems 226", "16", "2", "Sem 2" },
                { "COS284", "Computer organisation and architecture 284", "16", "2", "Sem 2" },
                { "COS210", "Theoretical computer science 210", "8", "2", "Sem 1" },
                { "WTW248", "Vector analysis 248", "12", "2", "Sem 2" },
                { "PHY255", "Waves, thermodynamics and modem physics 255", "24", "2", "Sem 1" },
                { "PHY263", "General physics 263", "24", "2", "Sem 2" },
                { "WTW211", "Linear algebra 211", "12", "2", "Sem 1" },
                { "WTW218", "Calculus 218", "12", "2", "Sem 1" },
                { "WTW220", "Analysis 220", "12", "2", "Sem 2" },
                { "COS314", "Artificial intelligence 314", "18", "3", "Sem 1" },
                { "COS330", "Computer security and ethics 330", "18", "3", "Sem 2" },
                { "COS333", "Programming languages 333", "18", "3", "Sem 2" },
                { "COS344", "Computer graphics 344", "18", "3", "Sem 1" },
                { "PHY310", "Particle and astroparticle physics 310", "18", "3", "Sem 2" },
                { "PHY356", "Electronics, electromagnetism and quantum mechanics 356", "36", "3", "Sem 1" },
                { "PHY364", "Statistical mechanics, solid state physics and modelling 364", "36", "3", "Sem 2" },
                { "COS711", "Artificial Intelligence (II) 711", "15", "4", "Sem 2" },
                { "FSK700", "Physics 700", "135", "4", "Year" }
        };

        try {
            for (String[] mod : modules) {
                db.insert(mod);
            }

            db.generateIndexAll();
        } catch (DatabaseException e) {
            System.out.println("Error: " + e);
        }

        for (String[] row : db.database) {
            //System.out.println("yes please");
            if (row[0] != null) {
                int c = 0;
                for (String s : row) {
                    if (c++ == 1) {
                        System.out.print(String.format("%1$-75s", s));
                    } else {
                        System.out.print(s + "\t");
                    }
                }
                System.out.println();
            } /*else {
                print("");
              }*/
        }

        System.out.println(db.indexes[0]);
        print(validTreap(db.indexes[0]));
        //System.out.println("db.indexes[0]");
        System.out.println(db.indexes[1]);
        print(validTreap(db.indexes[1]));

        print("");

        test1(db, columns);
        test2();
    }

    public static void test1(Database db, String[] columns) {

        /* Test if removing one works */
        try {
            print(db.removeFirstWhere(columns[2], "16"));
            print(validTreap(db.indexes[0]));
            print(validTreap(db.indexes[1]));
            print(db.removeFirstWhere(columns[2], "16"));
            print(validTreap(db.indexes[0]));
            print(validTreap(db.indexes[1]));
            print(db.removeFirstWhere(columns[2], "16"));
            print(validTreap(db.indexes[0]));
            print(validTreap(db.indexes[1]));
            printDB(db.database);
        } catch (DatabaseException e) {
            print(ANSI_RED + "You should not be seeing this this error comes from your removeFirst Where"
                    + ANSI_RESET);
        }

        /* Test if remove all works */
        String[][] removed = null;
        try {
            //print(db.indexes[1].toString());
            print(ANSI_RED);
            removed = db.removeAllWhere(columns[2], "18");
            printDB(removed);
            print(ANSI_RESET);
            print(validTreap(db.indexes[0]));
            print(validTreap(db.indexes[1]));

            //print(db.indexes[1].toString());
            printDB(db.database);
        } catch (DatabaseException e) {
            print(ANSI_RED + "You should not be seeing this this error comes from your removeAllWhere"
                    + ANSI_RESET);
        }
        /* Test for removing data that doesnt exist */
        try {
            print(ANSI_RED);
            printDB(db.removeAllWhere(columns[2], "200"));
            print(ANSI_RESET);
            printDB(db.database);
        } catch (DatabaseException e) {
            print(ANSI_RED
                    + "You should not be seeing this this is a problem with your removeAllWhere not catching if if something isnt in the database");
        }

        try {
            print(ANSI_RED);
            print(db.removeFirstWhere(columns[0], "COS210"));
            print(ANSI_RESET);
        } catch (DatabaseException e) {
            print(ANSI_RED + "If you are seeing this then you have a problem with your remove on a treap"
                    + ANSI_RESET);
        }

        try {
            print(ANSI_RESET + ANSI_GREEN);
            for (String[] strings : removed) {
                print(strings);
                db.insert(strings);
            }
            print(ANSI_RESET);
            printDB(db.database);
        } catch (DatabaseException e) {
            print(ANSI_RED
                    + "You should not be seeing this this could be a problem with your remove or insert if its a duplicate error its your remove"
                    + ANSI_RESET);
        }


        try {
            //System.out.println(db.indexes[0]);
            //System.out.println(db.indexes[1]);
            print(ANSI_GREEN);
            print(db.findFirstWhere(columns[0], "COS314"));
            print(ANSI_RESET);
            printDB(db.database);
            //System.out.println(db.indexes[0]);
            //System.out.println(db.indexes[1]);
        } catch (DatabaseException e) {
            print(ANSI_RED
                    + "If you are seeing this then you could have a problem with your access or your findfirstwhere"
                    + ANSI_RESET);
        }

        try {
            print(ANSI_GREEN);
            printDB(db.findAllWhere(columns[2], "12"));
            print(ANSI_RESET);
        } catch (DatabaseException e) {
            print(ANSI_RED
                    + "This message should not be seen if you are seeing this then you have a problem with your findAllWhere"
                    + ANSI_RESET);
        }

        try {
            print(ANSI_GREEN);
            print(db.updateFirstWhere(columns[0], "COS314", "COS3000"));
            print(ANSI_RESET);
            printDB(db.database);
        } catch (DatabaseException e) {
            print(ANSI_RED
                    + "If you are seeing this then you have a problem with your Updatefunction which calls your remove and insert"
                    + ANSI_RESET);
        }

        try {
            print(ANSI_GREEN);
            print(db.updateFirstWhere(columns[2], "15", "10000"));
            print(ANSI_RESET);
            printDB(db.database);
        } catch (DatabaseException e) {
            print(ANSI_RED
                    + "If you are seing this then you have a problem with your updatefirst where when it comes to non indexed columns"
                    + ANSI_RESET);
        }

        try {
            print(ANSI_GREEN);
            printDB(db.updateAllWhere(columns[2], "12", "130"));
            print(ANSI_RESET);
            printDB(db.database);
        } catch (DatabaseException e) {
            print(ANSI_RED + "IF you are seeing this then you have a problem with your update all where"
                    + ANSI_RESET);
        }

        String[][] empty_grid = new String[0][0];
        try {
            print(ANSI_RED);
            String[][] null_Find = db.findAllWhere(columns[2], "ooga booga");
            if (null_Find.length == empty_grid.length) {
                print("This tests if you handle incorrect accesses properly");
            } else {
                print("Not ooga and not booga you tested find wrong");
            }
            print(ANSI_RESET);
        } catch (DatabaseException e) {
            print("If you are seeing this then you have a problem with your find all where throwing an error when its not supposed to "
                    + e.toString() + ANSI_RESET);
        }

        try {
            print(ANSI_RED);
            String[][] null_remove = db.removeAllWhere(columns[0], "hello");
            if (null_remove.length == empty_grid.length) {
                print("well done you found the correct edge case for a remove function");
            } else {
                print("OH no this ins't right");
            }
            print(ANSI_RESET);
        } catch (DatabaseException e) {
            print(ANSI_RED
                    + " if you see this then you have a problem with your removeallwhere relating to removing things that arent in the database specifically lookign at the indexed column"
                    + e.toString() + ANSI_RESET);
        }

        try {
            print(ANSI_RED);
            String[][] null_remove_number_2 = db.removeAllWhere(columns[2], "hello");
            if (null_remove_number_2.length == empty_grid.length) {
                print("well done you found the correct edge case for a remove function again");
            } else {
                print("OH no this ins't right hopefully you go the previous one");
            }
            print(ANSI_RESET);
        } catch (DatabaseException e) {
            print(ANSI_RED
                    + "If you see this then there is a problem with your removeallwhere for an unindexed column "
                    + e.toString() + ANSI_RESET);
        }

        print(ANSI_RED);
        try {
            db.insert(new String[] { "FSK700", "Physics 700", "135", "4", "Year" });
            print("This shouldn't have been printed something is wrong when you try to insert a row that already exists");
        } catch (DatabaseException e) {
            print("Don't worry this is an exception that was supposed to be thrown: " + e);
        }
        print(ANSI_RESET);

        print(ANSI_RED);
        try {
            db.insert(new String[] { "FSK700", "Physics 700", "135", "4" });
            print("This shouldn't have been printed something is wrong when you try to insert a row that already exists");
        } catch (DatabaseException e) {
            print("Don't worry this is an exception that was supposed to be thrown for incorrect structure: " + e);
        }
        print(ANSI_RESET);

    }

    public static void test2() {
        String[] columns = { "Module Code", "Description", "Credits", "Year", "Session" };
        Database db = new Database(columns, 30);

        String[][] modules = {
                { "LST110", "Language and study skills 110", "6", "1", "Sem 1" },
                { "WTW124", "Mathematics 124", "16", "1", "Sem 2" },
                { "UP0102", "Academic orientation 102", "0", "1", "Year" },
                { "WTW114", "Calculus 114", "16", "1", "Sem 1" },
                { "WTW123", "Numerical analysis 123", "8", "1", "Sem 2" },
                { "PHY114", "First course in physics 114", "16", "1", "Sem 1" },
                { "PHY124", "First course in physics 124", "16", "1", "Sem 2" },
                { "AIM102", "Academic information management 102", "6", "1", "Sem 2" },
                { "COS122", "Operating systems 122", "16", "1", "Sem 2" },
                { "COS132", "Imperative programming 132", "16", "1", "Sem 1" },
                { "COS110", "Program design: Introduction 110", "16", "1", "Sem 2" },
                { "COS151", "Introduction to computer science 151", "8", "1", "Sem 1" },
                { "COS212", "Data structures and algorithms 212", "16", "2", "Sem 1" },
                { "COS226", "Concurrent systems 226", "16", "2", "Sem 2" },
                { "COS284", "Computer organisation and architecture 284", "16", "2", "Sem 2" },
                { "COS210", "Theoretical computer science 210", "8", "2", "Sem 1" },
                { "WTW248", "Vector analysis 248", "12", "2", "Sem 2" },
                { "PHY255", "Waves, thermodynamics and modem physics 255", "24", "2", "Sem 1" },
                { "PHY263", "General physics 263", "24", "2", "Sem 2" },
                { "WTW211", "Linear algebra 211", "12", "2", "Sem 1" },
                { "WTW218", "Calculus 218", "12", "2", "Sem 1" },
                { "WTW220", "Analysis 220", "12", "2", "Sem 2" },
                { "COS314", "Artificial intelligence 314", "18", "3", "Sem 1" },
                { "COS330", "Computer security and ethics 330", "18", "3", "Sem 2" },
                { "COS333", "Programming languages 333", "18", "3", "Sem 2" },
                { "COS344", "Computer graphics 344", "18", "3", "Sem 1" },
                { "PHY310", "Particle and astroparticle physics 310", "18", "3", "Sem 2" },
                { "PHY356", "Electronics, electromagnetism and quantum mechanics 356", "36", "3", "Sem 1" },
                { "PHY364", "Statistical mechanics, solid state physics and modelling 364", "36", "3", "Sem 2" },
                { "COS711", "Artificial Intelligence (II) 711", "15", "4", "Sem 2" },
                //{ "COS711", "Artificial Intelligence (II) 711", "15", "4", "Sem 2" },
                //{ "COS711", "Artificial Intelligence (II) 711", "15", "4" },
                //{ "FSK700", "Physics 700", "135", "4", "Year" }
        };

        try {
            for (String[] mod : modules) {
                db.insert(mod);
            }

            db.generateIndexAll();
            try {
                db.insert(new String[] { "FSK700", "Physics 700", "135", "4" });
            } catch (DatabaseException e) {
                print("The next line should throw Invalid number of columns");
                System.out.println("Error: " + e);
            }

            try {
                db.insert(new String[] { "LST110", "Language and study skills 110", "6", "1", "Sem 1" });
            } catch (DatabaseException e) {
                print("The next line should throw Duplicate insert of: LST110");
                System.out.println("Error: " + e);
            }

            try {
                db.insert(new String[] { "FSK700", "Physics 700", "135", "4", "Year" });
            } catch (DatabaseException e) {
                print("The next line should throw Database full");
                System.out.println("Error: " + e);
            }

        } catch (DatabaseException e) {
            print("you shouldn't be seeing this!!!");
            System.out.println("Error: " + e);
        }
    }

    public static void print(String out) {
        System.out.println(out);
    }

    public static void print(String[] out) {
        int c = 0;
        //System.out.print(ANSI_RED);
        for (String s : out) {
            if (c++ == 1) {
                System.out.print(String.format("%1$-75s", s));
            } else {
                System.out.print(s + "\t");
            }
        }
        System.out.println(/*ANSI_RESET*/);
    }

    public static void printDB(String[][] db) {
        for (String[] row : db) {
            //System.out.println("yes please");
            if (row[0] != null) {
                int c = 0;
                for (String s : row) {
                    if (c++ == 1) {
                        System.out.print(String.format("%1$-75s", s));
                    } else {
                        System.out.print(s + "\t");
                    }
                }
                System.out.println();
            }
        }
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static <T extends Comparable<T>> String validTreap(Treap<T> t) {
        return (validTreap(t.root) ? ANSI_GREEN + "Valid\n" + ANSI_RESET : ANSI_RED + "Invalid\n" + ANSI_RESET);
    }

    public static <T extends Comparable<T>> boolean validTreap(Node<T> n) {
        if (n == null) {
            return true;
        }

        if (n.left != null && (n.left.priority > n.priority || n.getData().compareTo(n.left.getData()) < 0)) {
            return false;
        }

        if (n.right != null && (n.right.priority > n.priority || n.getData().compareTo(n.right.getData()) > 0)) {
            return false;
        }

        if (!validTreap(n.left)) {
            return false;
        }

        if (!validTreap(n.right)) {
            return false;
        }

        return true;
    }
}
