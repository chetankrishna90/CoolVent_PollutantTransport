/* 20090616 This code suffered a major cleanup by MAMB.
 * 20120708 This code suffered a major cleanup by FADE.
 * Calculation.java
 *
 * Created on 2003 2:29
 */
//package mmpn1;
public class Calculation implements java.io.Serializable {

    /** Creates a new instance of Calculation */
    public Calculation() {
    }
    //the extened module for a fan-curved fan flowrate calculation

    public static void calFlowrate(Zone[] zones, int zoneNumber, boolean[][] connection, int fanConnect[][], double[][] flowrate, Flowpath[][] flowPath, double[][] delta_p, boolean fanSelect, double gamma, boolean oneZone) {
        //int constantPfan = 0;// pre-set to be an ordinary fan
        double sf = 1;
        double phi = 1;
        double f;
        for (int i = 0; i < zoneNumber; i++) {
            zones[i].calAir_density();
            for (int j = 0; j < zoneNumber; j++) {
                zones[j].calAir_density();
                if (connection[i][j] == true) {
                    if (oneZone && (fanConnect[i][j] == 1 || fanConnect[i][j] == -1)) { //If there is a fan in a single zone XV building
                        if (zones[i].getIsIntZone()) { //If flow goes from the interior to the exterior
                            if ((flowPath[i][j].getHeight() * flowPath[i][j].getWidth()) > (Math.PI / 4 * Math.pow(flowPath[i][j].fanD, 2))) { //FADE: More than one fan
                                sf = Math.floor((flowPath[i][j].getHeight() * flowPath[i][j].getWidth()) / (Math.PI / 4 * Math.pow(flowPath[i][j].fanD, 2)));
                                phi = 1;
                            } else { //FADE: One fan adjust for size
                                phi = flowPath[i][j].fanD / flowPath[i][j].getWidth();
                                sf = 1;
                            }
                            //<editor-fold defaultstate="collapsed" desc="Previous fan code">
                            //                        if (fanSelect) { //FADE: CoolVent will select the best fan according to user specifications
                            //                            double a = flowPath[i][j].a2 * Math.pow(Q_user, 2) * Math.pow(phi, 6) / Math.pow(sf, 2) - Math.abs(delta_p[i][j] * Math.pow(phi, 2));
                            //                            double b = flowPath[i][j].a1 * Q_user * Math.pow(phi, 3) / sf;
                            //                            double c = flowPath[i][j].a0;
                            //                            gamma = -b / (2 * a) - Math.sqrt(Math.pow(b, 2) - 4 * a * c) / (2 * a);
                            //                            if (gamma < 0.833) { //Limit fan speed to 1.2 times the rated speed
                            //                                gamma = 0.833;
                            //                            }
                            //                        }
                            //</editor-fold>
                            double d = flowPath[i][j].a2 * Math.pow(gamma, 2) * Math.pow(phi, 6) / Math.pow(sf, 2);
                            double e = flowPath[i][j].a1 * gamma * Math.pow(phi, 3) / sf;
                            if (fanConnect[i][j] == 1) { //FADE: DeltaP_fan,rise = Pi - Pj
                                f = flowPath[i][j].a0 - delta_p[j][i] * Math.pow(gamma, 2) * Math.pow(phi, 2);
                            } else { //FADE: DeltaP_fan,rise = Pj - Pi
                                f = flowPath[i][j].a0 - delta_p[i][j] * Math.pow(gamma, 2) * Math.pow(phi, 2);
                            }
                            if (4 * d * f > Math.pow(e, 2)) { //FADE: DeltaP is larger than DeltaP_max: No flow reversal in CoolVent (yet)
                                flowrate[i][j] = 0;
                            } else {
                                if (fanConnect[i][j] == 1) { //FADE: Flow goes from I (intZone) to J (ambientZone)
                                    flowrate[i][j] = zones[i].getAir_density() * (-e / (2.0 * d) - 1.0 / (2.0 * d) * Math.sqrt(Math.pow(e, 2) - 4 * d * f));
                                } else if (fanConnect[i][j] == -1) { //FADE: Flow goes from I (intZone) to J (ambientZone)
                                    flowrate[i][j] = -zones[i].getAir_density() * (-e / (2.0 * d) - 1.0 / (2.0 * d) * Math.sqrt(Math.pow(e, 2) - 4 * d * f));
                                }
                            }
                        } else { //Flow is from exterior to interior => regular opening
                            if (delta_p[i][j] >= 0.0) { //FADE: Changed volumetric flow rate to mass flow rate
                                flowrate[i][j] = zones[i].getAir_density() * flowPath[i][j].getHeight() * flowPath[i][j].getWidth() * flowPath[i][j].getCd() * Math.pow(2.0 * Math.abs(delta_p[i][j]) / zones[i].getAir_density(), flowPath[i][j].getPowerN()) * flowPath[i][j].openingFraction;
                                //flowrate[i][j] = flowPath[i][j].getHeight() * flowPath[i][j].getWidth() * flowPath[i][j].getCd() * Math.pow(2.0 * Math.abs(delta_p[i][j]) / zones[i].getAir_density(), flowPath[i][j].getPowerN()) * flowPath[i][j].openingFraction; //20080413 MAMB added the *openingfrac term at the end of the previous line
                            } else if (delta_p[i][j] < 0.0) { //FADE: Changed volumetric flow rate to mass flow rate
                                flowrate[i][j] = -zones[j].getAir_density() * flowPath[i][j].getHeight() * flowPath[i][j].getWidth() * flowPath[i][j].getCd() * Math.pow(2.0 * Math.abs(delta_p[i][j]) / zones[j].getAir_density(), flowPath[i][j].getPowerN()) * flowPath[i][j].openingFraction;
                                //flowrate[i][j] = -flowPath[i][j].getHeight() * flowPath[i][j].getWidth() * flowPath[i][j].getCd() * Math.pow(2.0 * Math.abs(delta_p[i][j]) / zones[j].getAir_density(), flowPath[i][j].getPowerN()) * flowPath[i][j].openingFraction; //20080413 MAMB added the *openingfrac term at the end of the previous line
                            }
                        }
                    } else if ((fanConnect[i][j] == 1 || fanConnect[i][j] == -1) && flowPath[i][j].isOn) { //FADE: Fan in flowpath. Calculate scale factor
                        if ((flowPath[i][j].getHeight() * flowPath[i][j].getWidth()) > (Math.PI / 4 * Math.pow(flowPath[i][j].fanD, 2))) { //FADE: More than one fan
                            sf = Math.floor((flowPath[i][j].getHeight() * flowPath[i][j].getWidth()) / (Math.PI / 4 * Math.pow(flowPath[i][j].fanD, 2)));
                            phi = 1;
                        } else { //FADE: One fan adjust for size
                            phi = flowPath[i][j].fanD / flowPath[i][j].getWidth();
                            sf = 1;
                        }
                        //<editor-fold defaultstate="collapsed" desc="Previous fan code">
                        //                        if (fanSelect) { //FADE: CoolVent will select the best fan according to user specifications
                        //                            double a = flowPath[i][j].a2 * Math.pow(Q_user, 2) * Math.pow(phi, 6) / Math.pow(sf, 2) - Math.abs(delta_p[i][j] * Math.pow(phi, 2));
                        //                            double b = flowPath[i][j].a1 * Q_user * Math.pow(phi, 3) / sf;
                        //                            double c = flowPath[i][j].a0;
                        //                            gamma = -b / (2 * a) - Math.sqrt(Math.pow(b, 2) - 4 * a * c) / (2 * a);
                        //                            if (gamma < 0.833) { //Limit fan speed to 1.2 times the rated speed
                        //                                gamma = 0.833;
                        //                            }
                        //                        }
                        //</editor-fold>
                        double d = flowPath[i][j].a2 * Math.pow(gamma, 2) * Math.pow(phi, 6) / Math.pow(sf, 2);
                        double e = flowPath[i][j].a1 * gamma * Math.pow(phi, 3) / sf;
                        if (fanConnect[i][j] == 1) { //FADE: DeltaP_fan,rise = Pi - Pj
                            f = flowPath[i][j].a0 - delta_p[j][i] * Math.pow(gamma, 2) * Math.pow(phi, 2);
                        } else { //FADE: DeltaP_fan,rise = Pj - Pi
                            f = flowPath[i][j].a0 - delta_p[i][j] * Math.pow(gamma, 2) * Math.pow(phi, 2);
                        }
                        if (4 * d * f > Math.pow(e, 2)) { //FADE: DeltaP is larger than DeltaP_max: No flow reversal in CoolVent (yet)
                            flowrate[i][j] = 0;
                        } else {
                            if (fanConnect[i][j] == 1) { //FADE: Flow goes from I (intZone) to J (ambientZone)
                                flowrate[i][j] = zones[i].getAir_density() * (-e / (2.0 * d) - 1.0 / (2.0 * d) * Math.sqrt(Math.pow(e, 2) - 4 * d * f));
                            } else if (fanConnect[i][j] == -1) { //FADE: Flow goes from I (intZone) to J (ambientZone)
                                flowrate[i][j] = -zones[i].getAir_density() * (-e / (2.0 * d) - 1.0 / (2.0 * d) * Math.sqrt(Math.pow(e, 2) - 4 * d * f));
                            }
                        }
                        //<editor-fold defaultstate="collapsed" desc="Previous fan code">
                        //                            if ((flowPath[i][j].getHeight() * flowPath[i][j].getWidth()) > (Math.PI / 4 * Math.pow(flowPath[i][j].fanD, 2))) { //FADE: More than one fan
                        //                                if (Math.abs(delta_p[i][j]) > (flowPath[i][j].a0 - (Math.pow(flowPath[i][j].a1, 2)) / (4 * flowPath[i][j].a2))) { //FADE: DeltaP is larger than DeltaP_max
                        //                                    flowrate[i][j] = Math.floor((flowPath[i][j].getHeight() * flowPath[i][j].getWidth()) / (Math.PI / 4 * Math.pow(flowPath[i][j].fanD, 2))) * (-flowPath[i][j].a1 / (2 * flowPath[i][j].a2));
                        //                                } else {
                        //                                    flowrate[i][j] = Math.floor((flowPath[i][j].getHeight() * flowPath[i][j].getWidth()) / (Math.PI / 4 * Math.pow(flowPath[i][j].fanD, 2))) * -zones[i].getAir_density() * ((flowPath[i][j].a1 / (2.0 * flowPath[i][j].a2) + 1.0 / (2.0 * flowPath[i][j].a2) * Math.sqrt(Math.pow(flowPath[i][j].a1, 2) - 4 * flowPath[i][j].a2 * (flowPath[i][j].a0 - Math.abs(delta_p[i][j])))));
                        //                                }
                        //                            } else { //FADE: One fan
                        //                                if (Math.abs(delta_p[i][j]) > (flowPath[i][j].a0 * Math.pow(phi, 2) - (Math.pow(flowPath[i][j].a1, 2)) / (4 * flowPath[i][j].a2 / Math.pow(phi, 2)))) { //FADE: DeltaP is larger than DeltaP_max
                        //                                    flowrate[i][j] = Math.pow(flowPath[i][j].getWidth() / flowPath[i][j].fanD, 2) * (-flowPath[i][j].a1 / (2 * flowPath[i][j].a2));
                        //                                    System.out.println("MAX P");
                        //                                } else {
                        //                                    flowrate[i][j] = -zones[i].getAir_density() * ((flowPath[i][j].a1 / (2.0 * flowPath[i][j].a2 * Math.pow(phi, 3)) + 1.0 / (2.0 * flowPath[i][j].a2 * Math.pow(phi, 3)) * Math.sqrt(Math.pow(flowPath[i][j].a1, 2) - 4 * flowPath[i][j].a2 * Math.pow(phi, 2) * (flowPath[i][j].a0 / Math.pow(phi, 2) - Math.abs(delta_p[i][j])))));
                        //                                    //System.out.println("phi: "+phi);
                        //                                }
                        //                            }
                        //                        } else if (fanConnect[i][j] == -1) { //Flow goes from J (intZone) to I (ambientZone)
                        //                            if ((flowPath[i][j].getHeight() * flowPath[i][j].getWidth()) > (Math.PI / 4 * Math.pow(flowPath[i][j].fanD, 2))) { //FADE: More than one fan
                        //                                if (Math.abs(delta_p[i][j]) > (flowPath[i][j].a0 - (Math.pow(flowPath[i][j].a1, 2)) / (4 * flowPath[i][j].a2))) { //FADE: DeltaP is larger than DeltaP_max
                        //                                    flowrate[i][j] = -Math.floor((flowPath[i][j].getHeight() * flowPath[i][j].getWidth()) / (Math.PI / 4 * Math.pow(flowPath[i][j].fanD, 2))) * (-flowPath[i][j].a1 / (2 * flowPath[i][j].a2));
                        //                                    //System.out.println("MAX P");
                        //                                } else {
                        //                                    flowrate[i][j] = Math.floor((flowPath[i][j].getHeight() * flowPath[i][j].getWidth()) / (Math.PI / 4 * Math.pow(flowPath[i][j].fanD, 2))) * zones[j].getAir_density() * ((flowPath[i][j].a1 / (2.0 * flowPath[i][j].a2) + 1.0 / (2.0 * flowPath[i][j].a2) * Math.sqrt(Math.pow(flowPath[i][j].a1, 2) - 4 * flowPath[i][j].a2 * (flowPath[i][j].a0 - Math.abs(delta_p[i][j])))));
                        //                                    //System.out.println("Multiplier: "+Math.floor((flowPath[i][j].getHeight() * flowPath[i][j].getWidth()) / (Math.PI /4 * Math.pow(flowPath[i][j].fanD,2))));
                        //                                }
                        //                            }
                        //                        } else { //FADE: One fan. Adjust for size:
                        //                            phi = flowPath[i][j].fanD / flowPath[i][j].getWidth();
                        //                            if (Math.abs(delta_p[i][j]) > (flowPath[i][j].a0 * Math.pow(phi, 2) - (Math.pow(flowPath[i][j].a1, 2)) / (4 * flowPath[i][j].a2 / Math.pow(phi, 2)))) { //FADE: DeltaP is larger than DeltaP_max
                        //                                flowrate[i][j] = -Math.pow(flowPath[i][j].getWidth() / flowPath[i][j].fanD, 2) * (-flowPath[i][j].a1 / (2 * flowPath[i][j].a2));
                        //                                System.out.println("MAX P");
                        //                            } else {
                        //                                flowrate[i][j] = zones[i].getAir_density() * ((flowPath[i][j].a1 / (2.0 * flowPath[i][j].a2 * Math.pow(phi, 3)) + 1.0 / (2.0 * flowPath[i][j].a2 * Math.pow(phi, 3)) * Math.sqrt(Math.pow(flowPath[i][j].a1, 2) - 4 * flowPath[i][j].a2 * Math.pow(phi, 2) * (flowPath[i][j].a0 / Math.pow(phi, 2) - Math.abs(delta_p[i][j])))));
                        //                                //System.out.println("phi: "+phi);
                        //                            }
                        //                        }
                        /************************************** Jinchao's original fan code********************************
                         * //                    if (fanConnect[i][j]==1) {
                         * //                        if (constantPfan != 0)// do this for constant pressure model only
                         * //                            flowrate[i][j]= 0.0;
                         * //                        else
                         * //                            flowrate[i][j] = -flowPath[i][j].b/2.0/flowPath[i][j].a - 1.0/2.0/flowPath[i][j].a *Math.sqrt(flowPath[i][j].b*flowPath[i][j].b-4*flowPath[i][j].a*(flowPath[i][j].c-delta_p[j][i]));
                         * //
                         * //                    } else if (fanConnect[i][j] ==-1) {
                         * //                        if (constantPfan != 0) // do this for constant pressure model only
                         * //                            flowrate[i][j]= -0.0;
                         * //                        else
                         * //                            flowrate[i][j] = -( -flowPath[j][i].b/2.0/flowPath[j][i].a - 1.0/2.0/flowPath[j][i].a *Math.sqrt(flowPath[j][i].b*flowPath[j][i].b-4*flowPath[j][i].a*(flowPath[j][i].c-delta_p[j][i])) );
                         * //************************************** Jinchao's original fan code********************************/
                        //</editor-fold>
                    } else { //When fanConnect[i][j] == 0 or fan is off
                        if (delta_p[i][j] >= 0.0) { //FADE: Changed volumetric flow rate to mass flow rate
                            flowrate[i][j] = zones[i].getAir_density() * flowPath[i][j].getHeight() * flowPath[i][j].getWidth() * flowPath[i][j].getCd() * Math.pow(2.0 * Math.abs(delta_p[i][j]) / zones[i].getAir_density(), flowPath[i][j].getPowerN()) * flowPath[i][j].openingFraction;
                            //flowrate[i][j] = flowPath[i][j].getHeight() * flowPath[i][j].getWidth() * flowPath[i][j].getCd() * Math.pow(2.0 * Math.abs(delta_p[i][j]) / zones[i].getAir_density(), flowPath[i][j].getPowerN()) * flowPath[i][j].openingFraction; //20080413 MAMB added the *openingfrac term at the end of the previous line
                        } else if (delta_p[i][j] < 0.0) { //FADE: Changed volumetric flow rate to mass flow rate
                            flowrate[i][j] = -zones[j].getAir_density() * flowPath[i][j].getHeight() * flowPath[i][j].getWidth() * flowPath[i][j].getCd() * Math.pow(2.0 * Math.abs(delta_p[i][j]) / zones[j].getAir_density(), flowPath[i][j].getPowerN()) * flowPath[i][j].openingFraction;
                            //flowrate[i][j] = -flowPath[i][j].getHeight() * flowPath[i][j].getWidth() * flowPath[i][j].getCd() * Math.pow(2.0 * Math.abs(delta_p[i][j]) / zones[j].getAir_density(), flowPath[i][j].getPowerN()) * flowPath[i][j].openingFraction; //20080413 MAMB added the *openingfrac term at the end of the previous line
                        }
                    }
                }
                //<editor-fold defaultstate="collapsed" desc="Previous fan code">
                /*FADE: ***************************never happens****************************
                 * if (constantPfan != 0) {//calculate the fan flowrate, can only deal with situations where no more than one fan exist in a zone
                 * double temp = 0.0;
                 * int jtemp = -1;
                 * for (int j = 0; j < zoneNumber; j++) {
                 * if (connection[i][j] == true) {
                 * if (fanConnect[i][j] == 1) {
                 * jtemp = j;
                 * temp += 0.0;
                 * } else if (fanConnect[i][j] == -1) {
                 * jtemp = j;
                 * temp += -0.0;
                 * } else {
                 * temp += -flowrate[i][j];
                 * }
                 * }
                 * }
                 * if (jtemp != -1) {
                 * flowrate[i][jtemp] = temp;
                 * }
                 * }
                 * FADE: ***************************never happens****************************/
                //</editor-fold>
            }
        }
    }

    //Calculate the flowrate_error
    public static double calFlowrate_error(double[][] flowrate, int zoneNumber, Zone[] zones) {
        double flowrate_error = 0.0;
        double temp1, temp2;

        for (int i = 0; i < zoneNumber; i++) {
            temp1 = 0.0;
            temp2 = 0.0;

            for (int j = 0; j < zoneNumber; j++) {
                temp1 = temp1 + flowrate[j][i];
                temp2 = temp2 + Math.abs(flowrate[j][i]);
            }

            if (temp2 >= 1.0E-20) {
                if (flowrate_error <= (Math.abs(temp1) / temp2) && zones[i].getIsIntZone() == true) {
                    flowrate_error = Math.abs(temp1) / temp2;
                }
            }
        }

        return flowrate_error;
    }

//Calculate the new temp_error; This method is only to find the steady state because actually the temperature in each calculation step
//is correct because the Mass and Energy Conservation is met.
    public static double calTemp_error(Zone[] zones, int zoneNumber, double[] temp_old) {
        double temp_error = 0.0;
        for (int i = 0; i < zoneNumber; i++) {
            if (temp_error <= Math.abs(zones[i].getAir_temp() - temp_old[i])) {
                temp_error = Math.abs(zones[i].getAir_temp() - temp_old[i]);
            }
        }
        return temp_error;
    }
//
//    public static void calFlowrate_crossvent(Zone[] zones, int zoneNumber, boolean[][] connection, int fanConnect[][], double[][] flowrate, Flowpath[][] flowPath, double[][] delta_p) {
//      double ACd_eff = 1/Math.pow((zoneNum-1)/Math.pow(flowPath.height*cvinternalwindowsize*.75, 2) + 2/Math.pow(cvsidewindowsize*.65, 2), .5);
//            System.out.println("ACd_eff    " + ACd_eff);
//
//    }
}
