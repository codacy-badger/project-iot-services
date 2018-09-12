//package com.ibm.crl.mv.resource;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.MediaType;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.ibm.crl.mv.model.RespResult;
//import com.ibm.crl.mv.model.RespResult.ResStatus;
//import com.ibm.crl.mv.model.VSRespResult;
//import com.ibm.crl.mv.model.VSRespResult.VSResStatus;
//import com.ibm.crl.mv.operator.DocHandler;
//import com.ibm.crl.mv.utils.ConfigUtil;
//
//@Path("/api1")
//public class VSApplication {
//
//	private static Logger log = LoggerFactory.getLogger(VSApplication.class);
//
//	private static int API_VERSION = ConfigUtil.getAPIVersion();
//	private static String ASSET_CLASS = "org.poc.maximo.example.Sr";
//
////	@GET
////	@Path("/{version}/vs")
////	@Produces(MediaType.APPLICATION_JSON)
////	public String validation(@PathParam("version") int apiversion, @QueryParam("class") String asset, @QueryParam("id")String id, @QueryParam("filter")String filter, @QueryParam("download")boolean download) {
////
////		RespResult rr = new RespResult();
////
////		if (apiversion != API_VERSION) {
////			rr.setStatus(ResStatus.Fail);
////			String msg = "Error  request api path, pls check ";
////			rr.setMsg(msg);
////		}
////		
////		return "success";
////	}
//	
//	
//	@GET
//	@Path("/{version}/vs")
//	@Produces(MediaType.APPLICATION_JSON)
//	public VSRespResult validation(@PathParam("version") int apiversion, @QueryParam("class") String asset, @QueryParam("id") String id, @QueryParam("download")boolean download) {
////		https：//host:port/iotbc-service/rest/api/1/vs?clsss=xxx&id=xx&download=false
//		VSRespResult vResult = new VSRespResult();
//		
//		if (apiversion != API_VERSION) {
//			vResult.setResStatus(VSResStatus.Fail);
//			vResult.setMsg("Error  request api path, pls check");
//			vResult.setVsResult(null);
//			return vResult;
//		}
//		
//		
//		if (!asset.equalsIgnoreCase(ASSET_CLASS)) {
//			vResult.setResStatus(VSResStatus.Fail);
//			vResult.setMsg("Error  asset class is not correct，pls check");
//			vResult.setVsResult(null);
//			return vResult;
//		}
//		
//		if (StringUtils.isEmpty(id)) {
//			vResult.setResStatus(VSResStatus.Fail);
//			vResult.setMsg("Error  please check if the id is empty");
//			vResult.setVsResult(null);
//			return vResult;
//		}
//		
//		
//			return DocHandler.getInstance().validation(asset, id, download);
//		
//
//	}
//}
