


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.lang.StringUtils;

import com.qunar.qfwrapper.bean.booking.BookingInfo;
import com.qunar.qfwrapper.bean.booking.BookingResult;
import com.qunar.qfwrapper.bean.search.FlightDetail;
import com.qunar.qfwrapper.bean.search.FlightSearchParam;
import com.qunar.qfwrapper.bean.search.FlightSegement;
import com.qunar.qfwrapper.bean.search.ProcessResultInfo;
import com.qunar.qfwrapper.bean.search.RoundTripFlightInfo;
import com.qunar.qfwrapper.constants.Constants;
import com.qunar.qfwrapper.interfaces.QunarCrawler;
import com.qunar.qfwrapper.util.QFHttpClient;
import com.qunar.qfwrapper.util.QFPostMethod;

public class Wrapper_gjsairsw001 implements QunarCrawler {

	private static final NameValuePair REQUESTOR = new NameValuePair(
			"requestor",
			"itd.presentation.handler.page.air.PresAirSimpleReqsPageHandler");
	
	private static final NameValuePair TRIPTYPE = new NameValuePair(
			"/sessionWorkflow/productWorkflow[@product='Air']/tripRequirements/tripType",
			"Return");
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
		FlightSearchParam searchParam = new FlightSearchParam();
		searchParam.setDep("MLA");
		searchParam.setArr("JNB");
		searchParam.setDepDate("2014-09-16");
		searchParam.setRetDate("2014-10-01");
		searchParam.setTimeOut("60000");
		searchParam.setToken("");
		String html = new Wrapper_gjdairsw001().getHtml(searchParam);
		ProcessResultInfo result = new ProcessResultInfo();
		result = new  Wrapper_gjdairsw001().process(html,searchParam);
		if(result.isRet() && result.getStatus().equals(Constants.SUCCESS))
		{
			List<RoundTripFlightInfo> flightList = (List<RoundTripFlightInfo>) result.getData();
			for (RoundTripFlightInfo in : flightList){
				System.out.println("************" + in.getInfo().toString());
				System.out.println("++++++++++++" + in.getDetail().toString());
			}
		}
		else
		{
			System.out.println(result.getStatus());
		}
	}

	@Override
	public BookingResult getBookingInfo(FlightSearchParam arg0) {
		String bookingUrlPre = "https://airnamibia.sita.aero/itd/itd";
		BookingResult bookingResult = new BookingResult();
		
		BookingInfo bookingInfo = new BookingInfo();
		bookingInfo.setAction(bookingUrlPre);
		bookingInfo.setMethod("post");
		Map<String, String> map = new LinkedHashMap<String, String>();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String date1=arg0.getDepDate().replaceAll("-", "/");
		Date date=new Date(date1);
		date1=format.format(date);
		String date2=arg0.getRetDate().replaceAll("-", "/");
		date=new Date(date2);
		date2=format.format(date);
		map.put("requestor","itd.presentation.handler.page.air.PresAirSimpleReqsPageHandler");
		map.put("_retdateeu",date2);
		map.put("/sessionWorkflow/productWorkflow[@product='Air']/tripRequirements/tripType","Return");
		map.put("/sessionWorkflow/productWorkflow[@product='Air']/tripRequirements/allJourneyRequirements/numberOfTravellers[@key='Adult']","1");
		map.put("/sessionWorkflow/productWorkflow[@product='Air']/tripRequirements/allJourneyRequirements/numberOfTravellers[@key='Child']","0");
		map.put("/sessionWorkflow/productWorkflow[@product='Air']/tripRequirements/allJourneyRequirements/numberOfTravellers[@key='Infant']","0");
		map.put("/sessionWorkflow/productWorkflow[@product='Air']/tripRequirements/allJourneyRequirements/serviceFareClassType","Economy");
		map.put("_handler=itd.presentation.handler.request.air.PresAirSearchRequestHandler/_xpath=/sessionWorkflow/productWorkflow[@product='Air']","GO");
		map.put("_depdateeu",date1);
		map.put("/sessionWorkflow/productWorkflow[@product='Air']/travelSelection/journeySelection[1]/departLocation/selected","Airport."+arg0.getDep());
		map.put("/sessionWorkflow/productWorkflow[@product='Air']/travelSelection/journeySelection[1]/arriveLocation/selected","Airport."+arg0.getArr());
		map.put("Referer", "http://www.airnamibia.com.na/");
		bookingInfo.setContentType("UTF-8");
		bookingInfo.setInputs(map);		
		bookingResult.setData(bookingInfo);
		bookingResult.setRet(true);
		return bookingResult;
	}

	@Override
	public String getHtml(FlightSearchParam arg0) {
		QFPostMethod post = null;
		try {
			QFHttpClient httpClient = new QFHttpClient(arg0, false);
			httpClient.getParams().setCookiePolicy(
					CookiePolicy.BROWSER_COMPATIBILITY);
			post = new QFPostMethod("https://airnamibia.sita.aero/itd/itd");
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String date1=arg0.getDepDate().replaceAll("-", "/");
			Date date=new Date(date1);
			date1=format.format(date);
			String date2=arg0.getRetDate().replaceAll("-", "/");
			date=new Date(date2);
			date2=format.format(date);
			NameValuePair[] names = {
					REQUESTOR,
					new NameValuePair("_depdateeu", date1),
					ADULT,
					AIR,
					new NameValuePair(
							"_retdateeu", date2),
					new NameValuePair(
							"/sessionWorkflow/productWorkflow[@product='Air']/travelSelection/journeySelection[1]/departLocation/selected",
							"Airport."+arg0.getDep()),
					new NameValuePair(
							"/sessionWorkflow/productWorkflow[@product='Air']/travelSelection/journeySelection[1]/arriveLocation/selected",
							"Airport."+arg0.getArr()), CHILD, INFANT, REQUESTOR,
					SERVICEFARECLASSTYPE, TRIPTYPE };
			post.setRequestBody(names);
			post.setRequestHeader("Referer", "http://www.airnamibia.com.na/");
			post.getParams().setContentCharset("UTF-8");
			httpClient.executeMethod(post);
			String html=post.getResponseBodyAsString();
			return html;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
		return "Exception";
	}

	@Override
	public ProcessResultInfo process(String arg0, FlightSearchParam arg1) {
		String html = arg0;
		ProcessResultInfo result = new ProcessResultInfo();
		if ("Exception".equals(html)) {
			result.setRet(false);
			result.setStatus(Constants.CONNECTION_FAIL);
			return result;	
		}
		if (html.contains("\"Result\":false")){
			result.setRet(true);
			result.setStatus(Constants.NO_RESULT);
			return result;	
		}		
		String moneyUnit = "0";	
		if(html.indexOf("Total price including taxes: EUR")>0)
		{
			moneyUnit="EUR";
		}
		List<RoundTripFlightInfo> flightList = new ArrayList<RoundTripFlightInfo>();
		try {	
		if (html.indexOf("Flight and fare options")>0) {
			
			String [] htmls=html.split("Select a flight for your inbound journey");
			String[] check=StringUtils.substringsBetween(htmls[0],"<!--","//-->");
			List<String> list=new ArrayList<String>();
			for (int i = 0; i < check.length; i++) {
				if (check[i].indexOf("locD")>0&&check[i].indexOf("journeyNames[\"j\"+journey][journeyOption].seg")>0) {
					list.add(check[i]);
				}
			}
			String[] checkinfo=StringUtils.substringsBetween(htmls[1],"<!--","//-->");
			List<String> listinfo=new ArrayList<String>();
			for (int i = 0; i < checkinfo.length; i++) {
				if (checkinfo[i].indexOf("locD")>0&&checkinfo[i].indexOf("journeyNames[\"j\"+journey][journeyOption].seg")>0) {
					listinfo.add(checkinfo[i]);
				}
			}
			FlightDetail flightDetail = new FlightDetail();
			RoundTripFlightInfo flightinfo=new RoundTripFlightInfo();
			List<FlightSegement> segs = new ArrayList<FlightSegement>();
			List<FlightSegement> segsinfo = new ArrayList<FlightSegement>();
			List<String> flightNoList = new ArrayList<String>();
			List<String> flightInfoNoList = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				String flightString=list.get(i);
				FlightSegement seg = new FlightSegement();
				seg.setDeptime(StringUtils.substringBetween(flightString,"departdatetime = \"","\""));
				String flightNo= StringUtils.substringBetween(flightString,"flight = \"","\"").replaceAll("[^a-zA-Z\\d]", "");
				flightNoList.add(flightNo);
				seg.setFlightno(flightNo);
				seg.setDepairport(StringUtils.substringBetween(flightString,"locD = \"","\""));
				seg.setArrtime(StringUtils.substringBetween(flightString,"arrivedatetime = \"","\""));
				seg.setArrairport(StringUtils.substringBetween(flightString,"locA = \"","\""));
				String depdates=StringUtils.substringBetween(flightString,"departdayofweek = \"","\"");
				depdates=depdates.replace("&nbsp", "");
				String [] depdatess=depdates.split(";");
				String day=depdatess[1];
				String [] deps = arg1.getDepDate().split("-");
				deps[2]=day;
				seg.setDepDate(deps[0]+"-"+deps[1]+"-"+deps[2]);
				String arrdeta=StringUtils.substringBetween(flightString,"arrivedayofweek = \"","\"");
				arrdeta=arrdeta.replace("&nbsp", "");
				String [] arrdetass=arrdeta.split(";");
				String dayarr=arrdetass[1];
				deps[2]=dayarr;
				seg.setArrDate(deps[0]+"-"+deps[1]+"-"+deps[2]);
				segs.add(seg);
			}
			
			for (int i = 0; i < listinfo.size(); i++) {
				String flightString=listinfo.get(i);
				FlightSegement seg = new FlightSegement();
				seg.setDeptime(StringUtils.substringBetween(flightString,"departdatetime = \"","\""));
				String flightNo= StringUtils.substringBetween(flightString,"flight = \"","\"").replaceAll("[^a-zA-Z\\d]", "");
				flightInfoNoList.add(flightNo);
				seg.setFlightno(flightNo);
				seg.setDepairport(StringUtils.substringBetween(flightString,"locD = \"","\""));
				seg.setArrtime(StringUtils.substringBetween(flightString,"arrivedatetime = \"","\""));
				seg.setArrairport(StringUtils.substringBetween(flightString,"locA = \"","\""));
				String depdates=StringUtils.substringBetween(flightString,"departdayofweek = \"","\"");
				depdates=depdates.replace("&nbsp", "");
				String [] depdatess=depdates.split(";");
				String day=depdatess[1];
				String [] deps = arg1.getDepDate().split("-");
				deps[2]=day;
				seg.setDepDate(deps[0]+"-"+deps[1]+"-"+deps[2]);
				String arrdeta=StringUtils.substringBetween(flightString,"arrivedayofweek = \"","\"");
				arrdeta=arrdeta.replace("&nbsp", "");
				String [] arrdetass=arrdeta.split(";");
				String dayarr=arrdetass[1];
				deps[2]=dayarr;
				seg.setArrDate(deps[0]+"-"+deps[1]+"-"+deps[2]);
				segsinfo.add(seg);
			}
			String priceStr=StringUtils.substringBetween(htmls[0],"<a title=\"Rules and Restrictions\"","</a>");
			int pricescount=priceStr.indexOf(moneyUnit);
			Double price=Double.parseDouble(priceStr.substring(pricescount).split(" ")[1]);
			String priceStr1=StringUtils.substringBetween(htmls[1],"<a title=\"Rules and Restrictions\"","</a>");
			int pricescount1=priceStr1.indexOf(moneyUnit);
			Double price1=Double.parseDouble(priceStr1.substring(pricescount1).split(" ")[1]);
			String taxStr=StringUtils.substringBetween(htmls[1],"Total price including taxes: ","(");
			String tax=taxStr.split(" ")[1];
			flightDetail.setArrcity(arg1.getArr());
			flightDetail.setDepcity(arg1.getDep());
			String date=arg1.getDepDate().replaceAll("-", "/");
			Date date2=new Date(date);
			flightDetail.setDepdate(date2);
			flightDetail.setFlightno(flightNoList);
			flightDetail.setMonetaryunit(moneyUnit);
			flightDetail.setPrice(price+price1);
			flightDetail.setTax(Double.parseDouble(tax));
			flightDetail.setWrapperid("gjsairsw001");
			flightinfo.setDetail(flightDetail);
			flightinfo.setRetflightno(flightInfoNoList);
			flightinfo.setInfo(segs);
			flightinfo.setRetinfo(segsinfo);
			flightList.add(flightinfo);
		}
		if (html.indexOf("Please correct the error below")>0) {
			result.setRet(false);
			result.setStatus(Constants.INVALID_AIRLINE);
			return result;
		}
		if(flightList.size()==0)
		{
			result.setRet(false);
			result.setStatus(Constants.PARSING_FAIL);
			return result;
		}
		result.setRet(true);
		result.setStatus(Constants.SUCCESS);
		result.setData(flightList);		
		return result;
		} catch(Exception e){
			result.setRet(false);
			result.setStatus(Constants.PARSING_FAIL);
			return result;
		}
	}

}
