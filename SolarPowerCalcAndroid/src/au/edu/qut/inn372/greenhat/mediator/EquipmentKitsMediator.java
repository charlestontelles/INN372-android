package au.edu.qut.inn372.greenhat.mediator;

import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;

import au.edu.qut.inn372.greenhat.bean.AndroidAbstractBean;
import au.edu.qut.inn372.greenhat.bean.Equipment;
import au.edu.qut.inn372.greenhat.ws.CalculatorSoapClient;
/**
 *
 */
public class EquipmentKitsMediator {
	private CalculatorSoapClient soapClient = new CalculatorSoapClient();
	private ArrayList<Equipment> equipmentKits;
	
	/**
	 * Constructor which initialises the equipment kits object
	 */
	public EquipmentKitsMediator() {
		equipmentKits = new ArrayList<Equipment>();
	}
	
	/**
	 * Returns the equipment kits bean object 
	 * @return The equipment kits object
	 */
	public ArrayList<Equipment> getEquipmentKits() {
		return equipmentKits;
	}
	
	/**
	 * Performs remote soap call to get equipment kits 
	 */
	public void getEquipments(){
		SoapObject equipmentKitsSoap = soapClient.synchronousRequest((new Equipment()).getSoapObject(AndroidAbstractBean.OPERATION_GET_EQUIPMENTS));
		for(int i=0; i<equipmentKitsSoap.getPropertyCount(); i++) {
			SoapObject curEquipmentSoap = (SoapObject)(equipmentKitsSoap.getProperty(i));
			equipmentKits.add(new Equipment(curEquipmentSoap, AndroidAbstractBean.OPERATION_GET_EQUIPMENTS));
		}
	}
}
