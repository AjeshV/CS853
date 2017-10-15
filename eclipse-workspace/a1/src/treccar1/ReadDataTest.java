package treccar1;

import treccar1.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * User: dietz
 * Date: 12/9/16
 * Time: 5:17 PM
 */
public class ReadDataTest {


    public static void main(String[] args) throws Exception{
        System.setProperty("file.encoding", "UTF-8");

 /*       if (args.length<3) {
            System.out.println("Command line parameters: articlefile outlinefile paragraphfile");
            System.exit(-1);
        }
*/      
      //String articles = "C:\\Users\\Ajesh\\Desktop\\UNH\\Courses\\CS853\\Programming Assignment 1 Lucene Index for TREC Complex Answer Retrieval\\test200_all\\train.test200.cbor.article.qrels";//args[0];
      String outlines = "C:\\Users\\Ajesh\\Desktop\\UNH\\Courses\\CS853\\Programming Assignment 1 Lucene Index for TREC Complex Answer Retrieval\\test200_all\\train.test200.cbor.outlines";//args[1];
      //String paragraphs = "C:\\Users\\Ajesh\\Desktop\\UNH\\Courses\\CS853\\Programming Assignment 1 Lucene Index for TREC Complex Answer Retrieval\\test200_all\\train.test200.cbor.paragraphs"; //args[2];

 /*       final FileInputStream fileInputStream3 = new FileInputStream(new File(articles));
        for(Data.Page page: DeserializeData.iterableAnnotations(fileInputStream3)) {
            for (List<Data.Section> sectionPath : page.flatSectionPaths()){
                System.out.println(Data.sectionPathId(page.getPageId(), sectionPath)+"   \t "+Data.sectionPathHeadings(sectionPath));
            }
            System.out.println();
        }
        
        FileInputStream fileInputStream3 = new FileInputStream(new File(articles));
        byte[] value = new byte[1];
        try {
        	for (int total = 0; (total = fileInputStream3.read(value)) != -1;){
                System.out.write(value, 0, total);
            }
        } finally {
        	fileInputStream3.close();
        }

        System.out.println("\n\n");*/
//
//        final FileInputStream fileInputStream3 = new FileInputStream(new File("release.articles"));
//        for(Data.Page page: DeserializeData.iterableAnnotations(fileInputStream3)) {
//            for (List<String> line : page.flatSectionPaths()){
//                System.out.println(line);
//            }
//            System.out.println();
//        }
//
//        System.out.println("\n\n");
//
//        final FileInputStream fileInputStream4 = new FileInputStream(new File("release.articles"));
//        for(Data.Page page: DeserializeData.iterableAnnotations(fileInputStream4)) {
//            for (Data.Page.SectionPathParagraphs line : page.flatSectionPathsParagraphs()){
//                System.out.println(line.getSectionPath()+"\t"+line.getParagraph().getTextOnly());
//            }
//            System.out.println();
//        }


        System.out.println("\n\n");

        final FileInputStream fileInputStream = new FileInputStream(new File(outlines));
        for(Data.Page page: DeserializeData.iterableAnnotations(fileInputStream)) {
            System.out.println(page);
            System.out.println();
        }
        /*

        System.out.println("\n\n");

       final FileInputStream fileInputStream2 = new FileInputStream(new File(paragraphs));
        for(Data.Paragraph p: DeserializeData.iterableParagraphs(fileInputStream2)) {
            System.out.println(p);
            System.out.println();
        }
*/

    }
}