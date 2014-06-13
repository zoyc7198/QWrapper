

import org.apache.commons.httpclient.NameValuePair;

import com.qunar.qfwrapper.bean.booking.BookingResult;
import com.qunar.qfwrapper.bean.search.FlightSearchParam;
import com.qunar.qfwrapper.bean.search.ProcessResultInfo;
import com.qunar.qfwrapper.interfaces.QunarCrawler;

public class Wrapper_gjsairsw001 implements QunarCrawler {

	private static final NameValuePair REQUESTOR = new NameValuePair(
			"requestor",
			"itd.presentation.handler.page.air.PresAirSimpleReqsPageHandler");
	private static final NameValuePair _RETDATEEU = new NameValuePair(
			"_retdateeu", "");
	private static final NameValuePair TRIPTYPE = new NameValuePair(
			"/sessionWorkflow/productWorkflow[@product='Air']/tripRequirements/tripType",
			"OneWay");
	private static final NameValuePair ADULT = new NameValuePair(
			"/sessionWorkflow/productWorkflow[@product='Air']/tripRequirements/allJourneyRequirements/numberOfTravellers[@key='Adult']",
			"1");
	private static final NameValuePair CHILD = new NameValuePair(
			"/sessionWorkflow/productWorkflow[@product='Air']/tripRequirements/allJourneyRequirements/numberOfTravellers[@key='Child']",
			"0");
	private static final NameValuePair INFANT = new NameValuePair(
			"/sessionWorkflow/productWorkflow[@product='Air']/tripRequirements/allJourneyRequirements/numberOfTravellers[@key='Infant']",
			"0");
	private static final NameValuePair SERVICEFARECLASSTYPE = new NameValuePair(
			"/sessionWorkflow/productWorkflow[@product='Air']/tripRequirements/allJourneyRequirements/serviceFareClassType",
			"Economy");
	private static final NameValuePair AIR = new NameValuePair(
			"_handler=itd.presentation.handler.request.air.PresAirSearchRequestHandler/_xpath=/sessionWorkflow/productWorkflow[@product='Air']",
			"GO");
	
	
	public static void main(String[] args) {
		
	}

	@Override
	public BookingResult getBookingInfo(FlightSearchParam arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHtml(FlightSearchParam arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessResultInfo process(String arg0, FlightSearchParam arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
