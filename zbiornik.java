import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Zbiornik {
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("C:\\Users\\user\\Desktop\\Dane_PR2\\woda.txt");
        BufferedReader woda = new BufferedReader(fileReader);
        PrintWriter zapis=new PrintWriter("wyniki5.txt");

//        Zadanie 5.1

        Pattern r = Pattern.compile("(([0-9]{4})-[0-9]{2}-[0-9]{2})\\s(.*)");
        String wers= woda.readLine();
        List <String>daty=new ArrayList<>(); // Potrzebne do zadania 5.2
        List <Integer>litry=new ArrayList<>(); //Potrzebne do zadania 5.2
        Map <String,Integer> mapa=new LinkedHashMap<>();
        for (int i = 2008; i < 2018; i++) {
            mapa.put(i+"",0);
        }
        String data="";
        String rok="";
        int litr=0;
        do {
            Matcher m=r.matcher(wers);
            if (m.find()){
                rok=m.group(2);
                litr=Integer.parseInt(m.group(3));
                data=m.group(1);
                mapa.put(rok,(mapa.get(rok) + litr));
                daty.add(data);
                litry.add(litr);
            }
            wers=woda.readLine();
        }while (wers!=null);
        woda.close();
        mapa=sortByValue(mapa);
        String []arr= mapa.keySet().toArray(new String[0]);
        String zad51=arr[arr.length-1];
        zapis.println("Zadanie 5.1");
        zapis.println(zad51);

//        Zadanie 5.2

        int counter=0;
        int ctemp=0;
        String tempf="";
        String f="";
        String templ="";
        String l="";
        int g=0;
        for (int i = 0; i < daty.size()-1; i++) {
            if (litry.get(i)>10000&litry.get(i+1)>10000){
                tempf = daty.get(i);
                for (int j = i; j < daty.size()-1; j++) {
                    if (litry.get(j) > 10000 & litry.get(j + 1) > 10000) {
                    ctemp ++;
                    templ = daty.get(j + 1);
                    g=j+1;
                    }
                    else{
                        i=g;
                        if(ctemp>counter){
                            f=tempf;
                            l=templ;
                            counter = ctemp+1;
                        }
                        ctemp=0;
                        break;
                    }
                }
            }
        }
        zapis.println("Zadanie 5.2");
        zapis.println(counter);
        zapis.println(f);
        zapis.println(l);

//        Zadanie 5.3

        int temp=0;
        String []miesiace={"January","February","March","April","May","June","July","August","September","October","November","December"};
        int []dni={31,29,31,30,31,30,31,31,30,31,30,31};
        int []sumalitrow=new int[12];
        for (int i = 0; i < dni.length; i++) {
            for (int j = temp; j < dni[i]+temp; j++) {
                sumalitrow[i]+=litry.get(j);
            }
            temp+=dni[i];
        }
        zapis.println("Zadanie 5.3");
        for (int i = 0; i <sumalitrow.length ; i++) {
            zapis.println(miesiace[i]+"="+sumalitrow[i]);
        }

//        Zadanie 5.4

        int pomiar= 500000;
        int roznica=0;
        int pomiar2=500000;
        int counter2=0;
        List <String> lista= new ArrayList<>();
        for (int i = 0; i < daty.size(); i++) {
            if (pomiar>=800000){
                counter2++;
            }
//            if (i==31){                       //pokazuje stan zbiornika na dzień 2008-02-02 dla porównania z arkuszem maturalnym
//                System.out.println(pomiar);
//            }
            if (pomiar>1000000){
                pomiar=1000000;
                lista.add(daty.get(i));
            }
            pomiar-=Math.ceil(pomiar*0.02);
            pomiar+=litry.get(i);
        }
        pomiar=500000;
        for (int i = 0; i < daty.size(); i++) {
            pomiar2-=Math.ceil(pomiar2*0.02);
            pomiar2+=litry.get(i);
            if (pomiar2>pomiar){
                pomiar=pomiar2;
            }
        }
        zapis.println("Zadanie 5.4a");
        zapis.println(lista.get(0));
        zapis.println("Zadanie 5.4b");
        zapis.println(counter2);
        zapis.println("Zadanie 5.4c");
        zapis.println(pomiar);
        zapis.close();
    }
}
