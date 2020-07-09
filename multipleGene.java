package improvised_gene;
import edu.duke.*;
import java.io.*;


public class multipleGene {
    public String findGene(String dna,int where){
        int startIndex = dna.indexOf("ATG",where);
        if(startIndex==-1)return "";
        int taaIndex = findStopCodon(dna,startIndex,"TAA");
        int tagIndex = findStopCodon(dna,startIndex,"TAG");
        int tgaIndex = findStopCodon(dna,startIndex,"TGA");        
        
        System.out.println("start index, taa, tga, tag: " + where + " " + taaIndex + " " + tgaIndex + " " +tagIndex );
        
        int minIndex=0;
        
        
        if(minIndex==dna.length()) return "";
        //return dna.substring(startIndex,minIndex+3);
        
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
        /*int currIndex = dna.indexOf("TAA",startIndex+3);
        while(currIndex!=-1){
            if((currIndex-startIndex)%3==0) {
                return dna.substring(startIndex,currIndex+3);
            } else {
                currIndex = dna.indexOf("TAA",currIndex+1);
            }
        }
        return "";*/
    }
    
    public void printAllGenes(String dna){
        int startIndex=0;
        while(true){
            String currentGene=findGene(dna,startIndex);
            if(currentGene.isEmpty()){
                break;
            }
            System.out.println(currentGene);
            startIndex = dna.indexOf(currentGene,startIndex)+currentGene.length();
            System.out.println("Start Index: " + startIndex);
        }
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
        return dna.length();
    }
    public void testFindStop(){
        String dna = "xxxATGyyyzzzTAGxxxATGyyyzzzTGAxx";
        System.out.println(dna);
        printAllGenes(dna);
        //System.out.println(dex);
    }

}