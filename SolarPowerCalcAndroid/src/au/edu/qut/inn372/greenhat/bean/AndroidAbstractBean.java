package au.edu.qut.inn372.greenhat.bean;

import org.ksoap2.serialization.SoapObject;
/**
 * 
 * @author Charleston Telles
 *
 */
public abstract class AndroidAbstractBean {
    public static final String NAMESPACE = "http://ws.greenhat.inn372.qut.edu.au/";
    protected static final String OPERATION_GET_EQUIPMENTS_NAME = "getEquipments";
    public static final int OPERATION_GET_EQUIPMENTS = 0;
    protected static final String OPERATION_CALC_ENERGY_PRODUCTION_NAME = "calcEnergyProduction";
    public static final int OPERATION_CALC_ENERGY_PRODUCTION = 1;
    protected static final String OPERATION_SAVE_CALCULATION_NAME = "saveCalculation";
    public static final int OPERATION_SAVE_CALCULATION = 2;
    
    //added by Martins
    public static final String OPERATION_VALIDATE_CREDENTIALS_NAME = "validateCredentials";
    public static final int OPERATION_VALIDATE_CREDENTIALS = 3;
    
    public static final String OPERATION_SAVE_USER_PROFILE_NAME = "saveUserProfile";
    public static final int OPERATION_SAVE_USER_PROFILE = 4;
    
    public static final String OPERATION_GET_PANELS_NAME = "getPanels";
    public static final int OPERATION_GET_PANELS = 5;
    
    //added by Fabian
    public static final String OPERATION_GET_CALCULATIONS_NAME = "getCalculations";
    public static final int OPERATION_GET_CALCULATIONS = 6;
    
    public static final String OPERATION_DELETE_CALCULATION_NAME = "deleteCalculation";
    public static final int OPERATION_DELETE_CALCULATION = 7;
    
    public static final String OPERATION_GET_SUN_LIGHT_HOURS_NAME = "getSunLightHours";
    public static final int OPERATION_GET_SUN_LIGHT_HOURS = 8;
    
    protected SoapObject soapObject = null;
    
	public SoapObject getSoapObject(int soapOperation){
		this.setSoapObject(soapOperation);
		return soapObject;
	}
	
	protected abstract void setSoapObject(int soapOperation);
}
