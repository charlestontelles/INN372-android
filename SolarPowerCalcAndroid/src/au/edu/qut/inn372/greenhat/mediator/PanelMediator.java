package au.edu.qut.inn372.greenhat.mediator;

import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;

import au.edu.qut.inn372.greenhat.bean.AndroidAbstractBean;
import au.edu.qut.inn372.greenhat.bean.Panel;
import au.edu.qut.inn372.greenhat.ws.CalculatorSoapClient;
/**
 *
 */
public class PanelMediator {
	private CalculatorSoapClient soapClient = new CalculatorSoapClient();
	private ArrayList<Panel> panelList;
	
	/**
	 * Constructor which initialises the equipment kits object
	 */
	public PanelMediator() {
		panelList = new ArrayList<Panel>();
	}
	
	/**
	 * Returns the equipment kits bean object 
	 * @return The equipment kits object
	 */
	public ArrayList<Panel> getPanelList() {
		return panelList;
	}
	
	/**
	 * Performs remote soap call to get panels
	 */
	public void getPanels(){
		SoapObject panelSoap = soapClient.synchronousRequest((new Panel()).getSoapObject(AndroidAbstractBean.OPERATION_GET_PANELS));
		for(int i=0; i<panelSoap.getPropertyCount(); i++) {
			SoapObject curPanelSoap = (SoapObject)(panelSoap.getProperty(i));
			panelList.add(new Panel(curPanelSoap, AndroidAbstractBean.OPERATION_GET_PANELS));
		}
	}
}
