import java.lang.Math.*;

/**
 * Manage.java
 * (description)
 *
 * Created 8/31/2004
 * Major cleanup by MAMB 6/16/2009
 * Major cleanup by FADE 3/12
 * 
 * @author
 * @version 3/27/2012
 */

public class Manage implements java.io.Serializable{
    public static DataPool[] caseManage;
    
    public Manage(int caseNum) {
       caseManage=new DataPool[caseNum];
    }
    
    public void addCase(int caseCount){
        caseManage[caseCount] = new DataPool();
        caseManage[caseCount].setCaseName(""+caseCount);
    }
}
   
//    public static int addCase(String caseName){
//
//        long earlyTime;
//        Date temp=new Date();
//        earlyTime=temp.getTime();
//        System.out.println(earlyTime);
//        int earlyCase=-1;
//        int currentCase=-1;
//        for (int i=0; i<caseCount;i++) //FADE: i==0 & caseCount==50 -> True // i==51 & caseCount==50 -> False
//        {
//            if(caseManage[i]==null) //FADE: caseManage[0]==null (since it is uninitialized) -> True
//            {
//                caseManage[i]=new DataPool();
//                caseManage[i].setCaseName(caseName); //FADE: caseName is "Automated"
//                currentCase=i; //FADE: currentCase==0
//                i=50; //FADE i==50
//            }
//            else if (caseManage[i]!=null&&earlyTime>=caseManage[i].time)
//            {
//                earlyTime=caseManage[i].time;
//                earlyCase=i;
//            }
//        }
//        
//        if (earlyCase>=0&&earlyCase<=49) //FADE: earlyCase==-1 -> False
//        {
//            caseManage[earlyCase].setCaseName(caseName);
//            currentCase=earlyCase;
//        }
//            return currentCase; //FADE: returns 0
//    }