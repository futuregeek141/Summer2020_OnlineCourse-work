import edu.duke.*;
import java.io.*;

public class storageGene {
    
    
    
    
    /*public void printAllGenes(String dna){
        int startIndex=0;
        int geneCount=0;
        while(true){
            String currentGene=findGene(dna,startIndex);
            if(currentGene.isEmpty()){
                break;
            } 
            System.out.println(currentGene);
            geneCount++;
            startIndex = dna.indexOf(currentGene,startIndex)+currentGene.length();
            //System.out.println("Start Index: " + startIndex);
            System.out.println("GeneCount: " + geneCount);
        }

    }*/
    
    
    
    public double cgRatio(String dna){
        int len=dna.length();
        int count=0,c=0,g=0;
        //temp=dna.indexOf("C");
        for(int i=0,tempc=0,tempg=0;i<len;i++){
            if(i==0) {
                tempc=dna.indexOf("c");
                if(tempc>=0) {
                    c++; 
                    tempg=dna.indexOf("g");
                } if(tempg>=0) g++;   
                else break;
                /*
                 * 3 1,2 1
                 * 4 2,8 2
                 */
                //System.out.println("tempc, tempg: " + " " + tempc + " " + tempg);
            } else {
                if(dna.indexOf("c",++tempc)>=0) c++; else break;
                if(dna.indexOf("g",++tempg)>=0) g++; else break;
                //System.out.println("tempc, tempg: " + " " + tempc + " " + tempg);
            }
            //System.out.println("tempc, taa, tga, tag: " + where + " " + taaIndex + " " + tgaIndex + " " +tagIndex );

        }
        //System.out.println("cg: " + (c+g));
        double ans=((double)(c+g))/len;
        return ans;
    }
    
    public int findStopCodon(String dna,int startIndex, String stopcodon){
        int currIndex = dna.indexOf(stopcodon,startIndex+3);
        while(currIndex!=-1){
            int diff = currIndex-startIndex;
            if(diff%3==0){
                return currIndex;
            } else {
                currIndex = dna.indexOf(stopcodon,currIndex+1);
            }
        }
        return -1;
    }
    
    public String findGene(String dna,int where){
        int startIndex = dna.indexOf("atg",where);
        if(startIndex==-1)return "";
        int taaIndex = findStopCodon(dna,startIndex,"taa");
        int tagIndex = findStopCodon(dna,startIndex,"tag");
        int tgaIndex = findStopCodon(dna,startIndex,"tga");        
        
        //System.out.println("start index, taa, tga, tag: " + where + " " + taaIndex + " " + tgaIndex + " " +tagIndex );
        
        int minIndex=0;
        
        if(taaIndex == -1 || (tgaIndex!=-1 && tgaIndex<taaIndex)){
            minIndex=tgaIndex;
        } else {
            minIndex = taaIndex;
        }
        
        if(minIndex == -1 || (tagIndex!=-1 && tagIndex<minIndex)){
            minIndex=tagIndex;
        } 
        
        if(minIndex==-1) return "";
        return dna.substring(startIndex,minIndex+3);

    }
    
    public StorageResource getAllGenes(String dna){
        int startIndex=0;
        StorageResource genelist = new StorageResource();
        int geneCount=0;
        while(true){
            String currentGene=findGene(dna,startIndex);
            System.out.println(currentGene);
            if(currentGene.length()>60) {System.out.println(currentGene);}
            if(currentGene.isEmpty()){
                break;
            } 
            genelist.add(currentGene);
            //System.out.println("Current Gene: " + currentGene);
            geneCount++;
            System.out.println("GeneCount: " + geneCount);            
            startIndex = dna.indexOf(currentGene,startIndex)+currentGene.length();
            //System.out.println("Start Index: " + startIndex);
            
        }
        return genelist;
    }
    
    public void processGenes(StorageResource sr){
        int nine=0;
        int max=0;
        String start="";
        int cgnine=0;
        for(String g:sr.data()){
            if(g.length()>60) {nine++;}
            if(cgRatio(g)>0.35) {  cgnine++;}            
            max = Math.max(start.length(),g.length());
        }
        System.out.println("number60gene, CG3.5greater, length of longest gene: " + nine +" " + cgnine + " " + max);
    }
    
    public void testFindStop(){
        //String dna = "xxxATGyyyzzzTAGxxxATGyyyzzzTGAxx";
        //String dna = "ATGEGGCCGCCCGGGCCCGGGCCCGGGTAGUDWATGYUMGUMTAA";
        FileResource fr = new FileResource("brca1line.fa");
        String dna = fr.asString();

        //System.out.println(dna);
        System.out.println("Length of sample: " + dna.length());
        
        StorageResource genes= getAllGenes(dna);
        /*for(String g:genes.data()){
            System.out.println(g);
        }*/
        processGenes(genes);
        // printAllGenes(dna);
        //System.out.println(dex);
    }

}
