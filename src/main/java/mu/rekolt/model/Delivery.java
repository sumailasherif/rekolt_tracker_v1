package mu.rekolt.model;

import mu.rekolt.util.IDGenerator;

import java.util.ArrayList;
import java.util.Scanner;


public class Delivery implements Comparable<Delivery> {// we make every field private and final because a delivery's details never changes
    private  String deliveryId;
    private  String produceCode;
    private  double produceWeightKg;;
    private  int memberName;;
    private  int qualityScore;
    private String memberId;


    //I computed and stored these once,  in the constructor, instead of recalculating them every time we read them
    private  String grade;
    private  double Commission_amount;
    private  double transportLevyAmount;
    private  double netPayable;