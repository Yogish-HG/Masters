import java.io.*;

public class Main {
        public static void readLines(BufferedReader reader) throws IOException {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        public static void main(String[] args) throws IOException {
            BufferedReader reader;

            // Load the reader with multiple lines of input
            String s = """
                    12 12 34
                    0 0 3 h
                    0 3 4 v
                    0 3 3 h
                    2 3 4 v
                    2 2 3 h
                    4 2 3 v
                    4 1 4 h
                    8 0 4 h
                    10 2 3 v
                    6 2 3 v
                    0 11 3 v
                    0 9 3 h
                    1 9 3 v
                    1 7 4 h
                    2 11 3 v
                    3 8 4 v
                    0 5 4 h
                    1 5 3 v
                    4 4 3 h
                    6 6 3 v
                    2 10 4 h
                    5 11 4 v
                    5 11 4 h
                    7 11 4 v
                    5 8 4 h
                    9 10 3 h
                    10 10 4 v
                    8 7 4 h
                    8 8 4 v
                    6 5 4 h
                    9 5 4 v
                    8 2 4 h
                    9 4 3 h
                    11 5 4 v
                    air
                    all
                    and
                    ash
                    ask
                    can
                    ebb
                    fan
                    flu
                    ice
                    nab
                    nil
                    oaf
                    ski
                    vet
                    ache
                    aunt
                    babe
                    bald
                    blah
                    cant
                    daub
                    etch
                    evil
                    flea
                    kiwi
                    land
                    pawn
                    seen
                    snub
                    swab
                    tell
                    typo
                    user""";

            reader = new BufferedReader(new StringReader(s));
            FillInPuzzle f = new FillInPuzzle();
            f.loadPuzzle(reader);
            f.solve();
//            System.out.println(f.choices());
//            System.out.println(f.choices());

            PrintWriter p =new PrintWriter(System.out);
            f.print(p);


        }
    }


