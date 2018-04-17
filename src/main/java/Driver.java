import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.jfree.ui.RefineryUtilities;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/******************************************************************************************************************************************
/******************************THE**HAPPINESS**PURSUIT*******************************************************************************************
/*********************************************MIGHTY***************************************************************************************/
/*
//* Flow of DATA :
//* In Each Generation it has : Population and it has : Colonies and it has : Persons and it has its own : Chromosome and it has :
    Set of Genes  : Represent Specific Activity Encoded by specific A-G-T-C Sequence which may or may not be same
    with other Person, as happens Naturally.
 */
public class Driver  {
    public static final String SAMPLE_XLSX_FILE_PATH = "..\\HList.xlsx"; //Refer the Excel sheet for List of Activities
    static String [] Activity;
    static Double [] Timetaken;
    static Double [] Reward;
    static GeneSymbolTable geneST;
    final static Logger logger = Logger.getLogger(Driver.class);



    public static void main(String args[]) throws IOException, InvalidFormatException {
        // 1. Load Data from Excel Sheet ans Initialise Gene Symbol Table
        int totalNodes  = LoadDataFromExcelSheet();
        //2. Initilialise the Weighted Activity Graph
        if(totalNodes > 0) {
            ActivityNodeGraph g = new ActivityNodeGraph(totalNodes);

            // generate a complete graph with n vertices
            fillDatainGraph(g, totalNodes);

            SolutionGeneticAlgo ga = new SolutionGeneticAlgo(g,geneST); //
            //START *******TESTING PURPOSE ONLY ****************************
            System.out.println("Total Colony Created " + ga.population.getColonyCount());

            int Ccount = 0;
            for(Colony c :ga.population.colonies()) {
                logger.info("Colony :"+ Ccount);Ccount++;
                int pCount = 0;
                for(Person p:c.getAllPerson()){
                    logger.info("Person "+ pCount+ " ");pCount++;
                    logger.info(" Total Time :"+ p.getTotalTimeSpent());
                    logger.info("Total Reward Point "+ p.getfitnessScore());
                    logger.info(p.getAllGeneSequence());
                    logger.info(" ");
                }
                logger.info(" ");
            }
            //END *******TESTING PURPOSE ONLY ****************************
            Double [] allFittestPerson  = ga.doEvolution();

            makePlottingofResult(allFittestPerson);

            //START Testing for All Edge Connection
//            for (Edge e :g.edges()){
//                System.out.println(e.toString());
//            }
            //END Testing for All Edge Connection
        }
    }

    private static void makePlottingofResult(Double[] allFittestPerson) {

        PlottingTheResults pr = new PlottingTheResults("Genetic Algorithm - Best Person's Activity",allFittestPerson);
        pr.pack( );
        RefineryUtilities.centerFrameOnScreen( pr );
        pr.setVisible( true );
    }

    private static void fillDatainGraph(ActivityNodeGraph g,int n) {
        for (int i = 0; i < n; i++) {
            for(int j=0; j<n; j++) {
                Double rewardPoint = loadValueofReward(i);
                Double timeTaken = loadValueofTimeTaken(i);
                if(i!=j) {
                    Edge e = new Edge(i, j, rewardPoint, timeTaken);  //Creating Edge
                    g.addEdge(e);
                }
            }
        }
        System.out.println("Total Edges Created: "+ g.getTotalEdge());
        System.out.println("Total Nodes Created: "+ g.getTotalNode());
    }

    private static Double loadValueofTimeTaken(int i ) { return Timetaken[i]; }

    private static Double loadValueofReward(int i) { return Reward[i]; }

    private static int LoadDataFromExcelSheet() throws IOException, InvalidFormatException {
//        try {
            Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));
            Sheet sheet = workbook.getSheetAt(0);

            Activity = new String[sheet.getLastRowNum()];
            Timetaken = new Double[sheet.getLastRowNum()];
            Reward = new Double[sheet.getLastRowNum()];


            int totalRows = sheet.getLastRowNum();

            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();


                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getColumnIndex() == 1 && cell.getRowIndex() != 0) {
//                    System.out.println(cell.getRowIndex() + "\t" );
                        Activity[cell.getRowIndex() - 1] = cell.toString();
                    }
                    if (cell.getColumnIndex() == 2 && cell.getRowIndex() != 0) {
//                    System.out.println(cell.getRowIndex() + "\t" );
                        Timetaken[cell.getRowIndex() - 1] = Double.valueOf(cell.toString());
                    }
                    if (cell.getColumnIndex() == 3 && cell.getRowIndex() != 0) {
//                    System.out.println(cell.getRowIndex() + "\t" );
                        Reward[cell.getRowIndex() - 1] = Double.valueOf(cell.toString());
                    }
                }

            }

            logger.info("Excel Import Done ");
            initialiseGeneSymbolTable(sheet.getLastRowNum());

//        System.out.println("END");


//            for(int i = 0 ; i <50; i ++){
//                System.out.println(Activity[i] + " "+ Timetaken[i]+ " "+ Reward[i]) ;}

            return sheet.getLastRowNum();
//        } catch (Exception e) {
//            System.out.println("Please Check the File Location"+ e);
//        }
//     return 0;
    }

    private static void initialiseGeneSymbolTable(int n) {

        geneST = new GeneSymbolTable(n,Activity,Timetaken,Reward);
    }
}
