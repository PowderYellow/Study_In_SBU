import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;



public class WordCounter {
    // The following are the ONLY variables we will modify for grading.
    // The rest of your code must run with no changes.
    public static final Path FOLDER_OF_TEXT_FILES  = Paths.get("TxtFileFolder"); // path to the folder where input text files are located
    public static final Path WORD_COUNT_TABLE_FILE = Paths.get("WordCountTable.txt"); // path to the output plain-text (.txt) file
    public static final int  NUMBER_OF_THREADS     =  Runtime.getRuntime().availableProcessors();                // max. number of threads to spawn

    public static ArrayList<File> files = new ArrayList<>();
    public static ArrayList<Map<String, Integer>> tableOfData = new ArrayList<>();
    public static ArrayList<String> allTheWords = new ArrayList<>();

    public static void main(String... args) {
        // your implementation of how to run the WordCounter as a stand-alone multi-threaded program


        ArrayList<String> words = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();

        File folder = new File(WordCounter.FOLDER_OF_TEXT_FILES.toString());

        File[] listOfFiles = folder.listFiles();

        if(listOfFiles != null) {
            files = new ArrayList<>(Arrays.asList(listOfFiles));
        }

        Collections.sort(files);


        long start = System.currentTimeMillis();
        Thread t1 = new Thread();


        if(WordCounter.NUMBER_OF_THREADS >= files.size()) {

            for (File file : files) {
                if (file.isFile()) {
                    //System.out.println(file.getName());

                    t1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            parallelReadFile(file);
                        }
                    });

                    threads.add(t1);
                    t1.start();
                    //parallelReadFile(file);
                }
            }


            for (Thread t : threads) {
                try {
                    t.join();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }

        } else if(WordCounter.NUMBER_OF_THREADS < files.size()) {

            ArrayList<File> threadedFiles = new ArrayList<>();
            ArrayList<File> mainThreadFiles = new ArrayList<>();

            for(int i = 0; i < WordCounter.NUMBER_OF_THREADS; i++){
                threadedFiles.add(files.get(i));
            }

            for(int i = WordCounter.NUMBER_OF_THREADS; i < files.size(); i++){
                mainThreadFiles.add(files.get(i));
            }

            for(File file : threadedFiles){
                if (file.isFile()) {
                    //System.out.println(file.getName());

                    t1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            parallelReadFile(file);
                        }
                    });

                    threads.add(t1);
                    t1.start();
                }
            }

            for (Thread t : threads) {
                try {
                    t.join();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }

            for(File file : mainThreadFiles){
                parallelReadFile(file);
            }


        }

        long end = System.currentTimeMillis();


        try {
            PrintWriter writer = new PrintWriter(WordCounter.WORD_COUNT_TABLE_FILE.toString());
            //writer.append("\t\t\t\t");
            //writer.write(String.format("%20s"," "));

            for (File file : files) {
                if (file.isFile()) {
                    writer.write(String.format("%20s", file.getName()));
                    //writer.write(file.getName() + "\t\t");
                }
            }

            writer.write(String.format("%20s", "Total"));
            //writer.write("Total");
            writer.println();

            Collections.sort(allTheWords);

            for(String word: allTheWords){
                //String tableRow = word;
                writer.write(String.format("%-20s", word));

                int totalCount = 0;

                for(Map<String, Integer> someMap: tableOfData){

                    if(someMap.containsKey(word)){

                        //tableRow += "\t\t" + someMap.get(word);
                        writer.write(String.format("%-20s", someMap.get(word)));
                        totalCount += someMap.get(word);
                    }
                    else {
                        //tableRow += "\t\t" + "0";
                        writer.write(String.format("%-20s", "0"));
                    }

                }

                writer.write(String.format("%-20s", totalCount));
                //writer.write(tableRow);
                writer.println();
            }


            writer.close();


        } catch(IOException ioe){
            ioe.printStackTrace();
        }


        System.out.println(end - start);

    }


    public static synchronized void parallelReadFile(File file) {
        HashMap<String, Integer> newMap = newMap = new HashMap<>();
        try {
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()) {

                String line = reader.nextLine().replaceAll("\\p{Punct}", "").trim().toLowerCase();
                String[] split = line.split(" ");

                for (int i = 0; i < split.length; i++) {
                    newMap.put(split[i], countOccurences(line, split[i]));

                    if (!allTheWords.contains(split[i])) {
                        allTheWords.add(split[i]);
                    }

                }

            }

            tableOfData.add(newMap);
           // System.out.println(line);
        } catch(IOException ioe) {
             ioe.printStackTrace();
        }
    }


    static int countOccurences(String str, String word)
    {
        // split the string by spaces in a
        String[] a = str.split(" ");

        // search for pattern in a
        int count = 0;
        for (int i = 0; i < a.length; i++)
        {
            // if match found increase count
            if (word.equals(a[i]))
                count++;
        }

        return count;
    }



}