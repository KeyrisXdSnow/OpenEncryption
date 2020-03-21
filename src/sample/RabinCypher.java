package sample;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class RabinCypher {

    private String path = "D:\\Java\\Projects\\TI\\Laba3\\" ;
    private BigInteger q,p,n,b, y_p, y_q ;
    private String f1, f2, f3, f4;
    private StringBuilder encyptFile ;

    RabinCypher (BigInteger q, BigInteger p, BigInteger b ) {
        try {
            this.q = q;
            this.p = p;
            this.b = b;

            if (PrimeNumber.cheсkSimplicity(q) && PrimeNumber.checkСondition(q) &&
                    PrimeNumber.cheсkSimplicity(p) && PrimeNumber.checkСondition(p)) n = q.multiply(p);
            else {
                System.out.println("Error");
                this.q = null;
                this.p = null;
                this.b = null;
            }

        } catch (Exception e) {
            System.out.println("Error");
            this.q = null;
            this.p = null;
            this.b = null;

        }
    }

    void encryptFile () {
        encyptFile = new  StringBuilder();

        try (RandomAccessFile fileReader = new RandomAccessFile(path+"text.txt","rw")) {
            BufferedWriter fileWriter = new BufferedWriter(
                  new OutputStreamWriter(new FileOutputStream(path+"decryptFile.txt")));

                BigInteger readByte = BigInteger.valueOf(fileReader.read());
                System.out.print("text : ");
//65533
                while (readByte.compareTo(BigInteger.valueOf(-1)) != 0) {

                    BigInteger sum = readByte.add(b);
                    readByte = readByte.multiply(sum);
                    readByte = readByte.mod(n);

                    encyptFile.append(readByte + " ");
                    for (int i = 0 ; i < 4 ; i++ ) {
                        if (readByte.compareTo(BigInteger.valueOf(65000)) > 0 ) {
                            fileWriter.write(65000);
                            readByte = readByte.subtract(BigInteger.valueOf(65000)) ;
                            encyptFile.append(65000 + " ");
                        }
                        else {
                            fileWriter.write(readByte.intValue());
                            readByte = BigInteger.valueOf(0);
                            encyptFile.append(readByte + " ");
                        }
                    }

                    readByte = BigInteger.valueOf(fileReader.read());

                }
                fileWriter.close();
            } catch (IOException e) {
                    System.out.println("Error");
            }
            System.out.println();
        }

    void decryptFile () {
        BigInteger D, s, r ;
        ArrayList<Integer> bufferP = new ArrayList<>();
        ArrayList<Integer> bufferQ = new ArrayList<>();

        try (InputStreamReader file = new InputStreamReader(new FileInputStream(
                path+"decryptFile.txt"))){

            BigInteger readByte = BigInteger.valueOf(file.read());
            if (readByte.compareTo(BigInteger.valueOf(-1)) != 0)
            for (int i = 0 ; i < 3 ; i++ ) {
                int v = file.read();
                readByte = readByte.add(BigInteger.valueOf(v));
            }

            while (readByte.compareTo(BigInteger.valueOf(-1)) != 0) {

                D = b.pow(2);
                D = D.add(readByte.multiply(BigInteger.valueOf(4)));
                D = D.mod(n);

                s = D.pow((p.add(BigInteger.valueOf(1)).divide(BigInteger.valueOf(4))).intValue());
                s = s.mod(p);

                r = D.pow((q.add(BigInteger.valueOf(1)).divide(BigInteger.valueOf(4))).intValue());
                r = r.mod(q);

                advEuclAlgorithm(p, q);

                bufferP.add(((y_p.multiply(p.multiply(r))).add(y_q.multiply(q.multiply(s)))).intValue());
                bufferQ.add(((y_p.multiply(p.multiply(r))).subtract(y_q.multiply(q.multiply(s)))).intValue());


                readByte = BigInteger.valueOf(file.read());
                 if ( readByte.compareTo(BigInteger.valueOf(-1)) != 0) {
                    for (int i = 0; i < 3; i++) {
                        readByte = readByte.add(BigInteger.valueOf(file.read()));
                    }
                }

            }

            f1 = writeToFile(bufferP,"file1.txt",false);
            f2 = writeToFile(bufferP,"file2.txt",true);
            f3 = writeToFile(bufferQ,"file3.txt",false);
            f4 = writeToFile(bufferQ,"file4.txt",true);

        } catch (IOException e) {
            System.out.println("Error");
        }


    }

    private void advEuclAlgorithm (BigInteger a, BigInteger b ) {
        BigInteger d0 = a, d1 = b, d2,
                   x0 = BigInteger.valueOf(1), x2,
                   y0 = BigInteger.valueOf(0), y2 ,
                   q ;
        y_q = BigInteger.valueOf(1);
        y_p = BigInteger.valueOf(0);

        while (d1.compareTo(BigInteger.valueOf(1)) > 0) {
            q =  d0.divide(d1);
            d2 = d0.mod(d1);
            x2 = (x0.subtract(q.multiply(y_p)));
            y2 = (y0.subtract(q.multiply(y_q)));
            d0 = d1;
            d1 = d2;
            x0 = y_p;
            y_p = x2;
            y0 = y_q;
            y_q = y2;
        }
    }

    private String writeToFile (ArrayList<Integer> List, String fl, boolean neg) {
        int fByte ;
        StringBuilder strFile = new StringBuilder();
        try (FileWriter file = new FileWriter(path+fl)) {
            for ( int ibyte : List) {

                if (neg) ibyte = Math.floorMod(-ibyte,n.intValue());
                else ibyte = Math.floorMod(ibyte,n.intValue());

                if ( Math.floorMod(ibyte - b.intValue(),2) == 0 ) {
                    fByte =  Math.floorMod((-b.intValue() + ibyte)/2,n.intValue());
                    file.write(fByte);
                    strFile.append(fByte+" ");
                } else {
                    fByte = Math.floorMod((-b.intValue() + n.intValue() + ibyte)/2,n.intValue()) ;
                    file.write(fByte);
                    strFile.append(fByte+" ");
                }
            }

        } catch (IOException e) {
            System.out.println("Error");
        }
        return String.valueOf(strFile) ;
    }

    public String getF1() {
        return f1;
    }

    public String getF2() {
        return f2;
    }

    public String getF3() {
        return f3;
    }

    public String getF4() {
        return f4;
    }

    public String getEncryptFile() {
        return String.valueOf(encyptFile);
    }
}
