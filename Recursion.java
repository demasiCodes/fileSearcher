/***
 * @author Daniel DeMasi
 * September 15, 2022
 * CSE017 Fall 2022: ALA4
 * Recursion: searches through computer for a file
 * IDE: VSCode, Java JDK 11
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Recursion{
    public static void main(String[] args){
        Scanner keys = new Scanner(System.in);

        //test findWord
        System.out.println("Enter a path: ");
        String path = keys.nextLine();
        System.out.println("Enter a word: ");
        String word = keys.nextLine();
        findWord(path, word);

        //test getSize to report size of directory path in bytes
        System.out.println("Enter another path: ");
        path = keys.nextLine();
        long pathSize = getSize(path);
        String units = " bytes";
        double size = 0.0;
        if(pathSize > 1000000000){
            size = pathSize/1000000000.0;
            units = " Gbytes";
        }
        else if(pathSize > 1000000){
            size = pathSize/1000000.0;
            units = " Mbytes";
        }
        else if(pathSize > 1000){
            size = pathSize/1000.0;
            units = " Kbytes";
        }
        else{
            size = pathSize;
        }
        System.out.println("Size of " + path + ": " + size + units); //display results

        keys.close();
    }
    /***
     * Recursive method to search computer for a file that includes a certain word
     * @param path
     * @param word
     */
    public static void findWord(String path, String word){
        File f = new File(path);
        
        if(f.exists()){
            if(f.isDirectory()){
                File[] contents = f.listFiles();
                for(int i = 0; i < contents.length; i++){
                    findWord(contents[i].getAbsolutePath(), word); //recursion
                }
            }
            else if(f.isFile()){
                int wordCount = countWords(f, word);
                if(wordCount != 0){
                    System.out.println(word + " appears " + wordCount + " times in the file " + f.getAbsolutePath());
                }
            }
        }
        else { //invalid
            System.out.println("Invalid Path Name");
        }
    }
    /***
     * Method to read from a file and search each line for a specific word
     * @param file 
     * @param word 
     * @return count for the number of times word is found
     */
    public static int countWords(File file, String word){
        int count = 0;
        try{
            Scanner s = new Scanner(file);
            while(s.hasNextLine()){
                String line = s.nextLine();
                int index = line.indexOf(word, 0);
                while(index != -1){
                    count++;
                    index = line.indexOf(word, index + 1);
                }
            }
            s.close();
            return count;
        }
        catch(FileNotFoundException e){
            return 0;
        }
    }
    /***
     * Recursive method to get the size of the directory path in bytes
     * @param path
     * @return size of the path in bytes
     */
    public static long getSize(String path){
        File f = new File(path);
        long size = 0;
        
        if(f.exists()){
            if(f.isDirectory()){
                File[] contents = f.listFiles();
                for(int i = 0; i < contents.length; i++){
                    if(contents[i].isFile()){
                        size += contents[i].length();
                    }
                    else if(contents[i].isDirectory()){
                        size += getSize(contents[i].getAbsolutePath());
                    }
                }
            }
            else if(f.isFile()){
                size = f.length();
            }
        }
        else { //invalid
            System.out.println("Invalid Path Name");
        }
        return size;
    }

}