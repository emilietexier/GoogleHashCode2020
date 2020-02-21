package google_hash_code;

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) {
		File file = new File("input_file.text");
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<ArrayList<Long>> book_ids = new ArrayList<>();
        int b = 0;
        int l = 0;
        int d = 0;


        String s = in.nextLine();

        b = Integer.parseInt(s.split(" ")[0]);
        l = Integer.parseInt(s.split(" ")[1]);
        d = Integer.parseInt(s.split(" ")[2]);

        long[] num_books = new long[l];
        long[] signup_days = new long[l];
        long[] shipped = new long[l];

        String sc = in.nextLine();
        String[] scores_s = sc.split(" ");
        long[] scores = new long[scores_s.length];

        for (int i = 0; i < scores_s.length; ++i) {
            scores[i] = Long.parseLong(scores_s[i]);
        }

        for (int i = 0; i < l; ++i) {
            String s1 = in.nextLine();
            String[] test = s1.split(" ");

            num_books[i] = Long.parseLong(test[0]);
            signup_days[i] = Long.parseLong(test[1]);
            shipped[i] = Long.parseLong(test[2]);

            String s2 = in.nextLine();
            ArrayList<Long> ids = new ArrayList<>();

            for (int j = 0; j < s2.split(" ").length; ++j) {
                ids.add(Long.parseLong(s2.split(" ")[j]));
            }

            book_ids.add(ids);
        }

        double[] lib_scores = new double[l];
        for(int i = 0; i < l; ++i) {
            double score_moyen = 0;
            for(int j = 0; j < num_books[i]; ++j) {
                score_moyen += scores[book_ids.get(i).get(j).intValue()];
            }
            score_moyen = score_moyen / num_books[i];
            lib_scores[i] =  (score_moyen / signup_days[i]) * ((double)shipped[i] / num_books[i]);
        }

        long time = 0;
        long signup = 0;

        long[] day_registered = new long[l];
        for(int i = 0; i < l; ++i) {
            day_registered[i] = -1;
        }

        while(time < d) {
            double max = 0;
            for (int j = 0; j < l; ++j) {
                if (lib_scores[j] > max) {
                    max = lib_scores[j];
                    signup = j;
                }
            }
            lib_scores[(int)signup] = -1;
            time += signup_days[(int)signup];
            if (time > d) {
                break;
            }
            day_registered[(int)signup] = time + 1;
        }

        int num_todo = 0;
        for(int i = 0; i < l; ++i) {
            if(day_registered[i] < d && day_registered[i] > 0) {
                ++num_todo;
            }
        }

        long[] lib_indice = new long[num_todo];
        long[] lib_day = new long[num_todo];

        long[] tab = new long[num_todo];


        for(int i = 0; i < num_todo; ++i) {
            long min = d;
            for(int j = 0; j < l; ++j) {
                if (day_registered[j] < min && day_registered[j] > 0) {
                    lib_indice[i] = j;
                    lib_day[i] = day_registered[j];
                    tab[i] = lib_day[i];
                    min = day_registered[j];
                    day_registered[j] = -1;
                }
            }
        }


        long[] nb_livres_output = new long[num_todo];
        ArrayList<ArrayList<Long>>ids_print = new ArrayList<>();


        for(int x = 0; x < num_todo; ++x) {
            long effective = d - tab[x];
            long t = effective * shipped[(int)lib_indice[x]];
            if(t > num_books[(int)lib_indice[x]]){
                nb_livres_output[x] = num_books[(int)lib_indice[x]];
            }
            else{
                nb_livres_output[x] = effective * shipped[(int)lib_indice[x]];
            }
            ArrayList<Long> temp = new ArrayList<Long>();
            for(int j = 0; j < nb_livres_output[x]; ++j) {
                if(num_books[(int)lib_indice[x]] > j) {
                    temp.add(book_ids.get((int)lib_indice[x]).get(j));
                }
            }
            ids_print.add(temp);
        }

        write_file(num_todo, lib_indice, nb_livres_output, ids_print);
    }
	
	public static void write_file (int num, long[] lib_indice, long[] nb_books_output, ArrayList<ArrayList<Long>> book_ids) {

        try {
            PrintWriter writer = new PrintWriter("output_file.txt", "UTF-8");
            writer.println(num);
            System.out.println(num);
            for (int i = 0; i < num; ++i) {
                String s1 = lib_indice[i] + " " + nb_books_output[i];
                System.out.println(s1);
                writer.println(s1);
                String s2 = "";
                for (int j = 0; j < book_ids.get(i).size(); ++j) {
                    s2 += (book_ids.get(i).get(j) + " ");
                }
                writer.println(s2);
                System.out.println(s2);

            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

}
