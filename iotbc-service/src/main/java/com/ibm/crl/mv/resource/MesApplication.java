package com.ibm.crl.mv.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.crl.mv.maximo.model.AssetPostResult;
import com.ibm.crl.mv.maximo.model.AssetPostResult.SRResult;
import com.ibm.crl.mv.maximo.model.AssetRequest;
import com.ibm.crl.mv.maximo.model.SRHistModel;
import com.ibm.crl.mv.maximo.model.UpdateList;
import com.ibm.crl.mv.maximo.model.UpdateList.HistStatus;
import com.ibm.crl.mv.maximo.operator.MaximoHandler;
import com.ibm.crl.mv.model.BodyDescriptor;
import com.ibm.crl.mv.model.RespResult;
import com.ibm.crl.mv.model.VSRespResult;
import com.ibm.crl.mv.model.RespResult.ResStatus;
import com.ibm.crl.mv.model.VSRespResult.VSResStatus;
import com.ibm.crl.mv.operator.DocHandler;
import com.ibm.crl.mv.utils.ConfigUtil;


@Path("/api")
public class MesApplication {

	private static Logger log = LoggerFactory.getLogger(MesApplication.class);

	private static int API_VERSION = ConfigUtil.getAPIVersion();

	private ObjectMapper om = new ObjectMapper(new JsonFactory());
	
	private static String ASSET_CLASS = "org.poc.maximo.example.Sr";

	
	
	@Path("/{version}/mes")
	@POST
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	public RespResult ingestion(@PathParam("version") int apiversion, String body) {

		RespResult rr = new RespResult();

		if (apiversion != API_VERSION) {
			rr.setStatus(ResStatus.Fail);
			String msg = "Error  request api path, pls check ";
			rr.setMsg(msg);
			return rr;
		}

		if (StringUtils.isEmpty(body) || body.equals("{}")) {
			rr.setStatus(ResStatus.Fail);
			String msg = "Post request body is null, pls check ";
			rr.setMsg(msg);
			return rr;
		}

		BodyDescriptor desc = null;

		try {
			desc = om.readValue(body, BodyDescriptor.class);

			if (StringUtils.isEmpty(desc.getTicketid()) || StringUtils.isEmpty(desc.getLong_description())
					|| StringUtils.isEmpty(desc.getStatus()) || desc.getDocs().isEmpty()) {

				String msg = "There is  the field of null value   in request body";
				rr.setStatus(ResStatus.Fail);
				rr.setMsg(msg);
				return rr;

			}
		} catch (IOException e) {
			String msg = "The request body format is error, pls check!!!";
			log.error(msg, e);
			rr.setMsg(msg);
			rr.setStatus(ResStatus.Fail);
			return rr;
		}

		// request record unique id
		String record_ID = UUID.randomUUID().toString();

		return DocHandler.getInstance().handle(desc, record_ID);

	}


	@GET
	@Path("/{version}/vs")
	@Produces(MediaType.APPLICATION_JSON)
	public VSRespResult validation(@PathParam("version") int apiversion, @QueryParam("class") String asset,
			@QueryParam("id") String id, @QueryParam("download") boolean download) {
		VSRespResult vResult = new VSRespResult();

		if (apiversion != API_VERSION) {
			vResult.setResStatus(VSResStatus.Fail);
			vResult.setMsg("Error  request api path, pls check");
			vResult.setVsResult(null);
			return vResult;
		}

		if (!asset.equalsIgnoreCase(ASSET_CLASS)) {
			vResult.setResStatus(VSResStatus.Fail);
			vResult.setMsg("Error  asset class is not correct，pls check");
			vResult.setVsResult(null);
			return vResult;
		}

		if (StringUtils.isEmpty(id)) {
			vResult.setResStatus(VSResStatus.Fail);
			vResult.setMsg("Error  please check if the id is empty");
			vResult.setVsResult(null);
			return vResult;
		}

		return DocHandler.getInstance().validation(asset, id, download);

	}
	
	
	//http://localhost:8080/iotbc-service/rest/api/1/maximo/upload
	@POST
	@Path("{version}/maximo/upload")
	@Produces(MediaType.APPLICATION_JSON)
	public AssetPostResult uploadMsg(@PathParam("version") int apiversion, String body) {
		AssetPostResult apResult = new AssetPostResult();
		//log.info(body);
		
		if (apiversion != API_VERSION) {
			apResult.setMeg("ERROR:  request api path, pls check!!!");
			apResult.setsResult(SRResult.Fail);
			apResult.setsModel(new SRHistModel());
			return apResult;
		}
		
		if (StringUtils.isEmpty(body) || body.equals("{}")) {
			apResult.setMeg("ERROR:  request body is empty!!!");
			apResult.setsResult(SRResult.Fail);
			apResult.setsResult(null);
			return apResult;
		}
		
		AssetRequest ar = null;
		try {
			ar = om.readValue(body, AssetRequest.class);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			String msg = "ERROR: The request body format is error, pls check!!!";
			log.error(msg,e);
			apResult.setMeg(msg);
			apResult.setsResult(SRResult.Fail);
			apResult.setsModel(null);
			return apResult;
		}
		
		return MaximoHandler.getInstance().postSR(ar);
	}
	
	
	//http://localhost:8080/iotbc-service/rest/api/1/maximo//transaction/list
	@GET
	@Path("{version}/maximo/transaction/list")
	@Produces(MediaType.APPLICATION_JSON)
	public UpdateList getlist(@PathParam("version") int apiversion, @QueryParam("download") boolean download,@QueryParam("task") String task) {
		UpdateList uList = new UpdateList();
		log.info("获取请求开始!!!");
		
		if (apiversion != API_VERSION) {
			uList.sethStatus(HistStatus.Fail);
			uList.setDownload(false);
			uList.setMsg("ERROR:  request api path, pls check!!!");
			uList.setuList(new ArrayList<>());
			return uList;
		}
		
		if (StringUtils.isEmpty("1307")) {
			uList.sethStatus(HistStatus.Success);
			uList.setDownload(false);
			uList.setMsg("response null");
			uList.setuList(new ArrayList<>());
			return uList;
		}
		
		if (task.isEmpty()) {
			uList.sethStatus(HistStatus.Fail);
			uList.setDownload(false);
			uList.setMsg("ERROR:  request api path, pls check!!!");
			uList.setuList(new ArrayList<>());
			return uList;
		}
		
		return MaximoHandler.getInstance().getSrHist(download);
		
	}

}





