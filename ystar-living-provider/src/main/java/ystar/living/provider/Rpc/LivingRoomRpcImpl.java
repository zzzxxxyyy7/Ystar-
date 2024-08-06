package ystar.living.provider.Rpc;


import com.ystar.common.VO.PageWrapper;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import ystar.living.Vo.req.LivingRoomReqVO;
import ystar.living.Vo.resp.LivingRoomPageRespVO;
import ystar.living.dto.LivingRoomReqDTO;
import ystar.living.dto.LivingRoomRespDTO;
import ystar.living.interfaces.ILivingRoomRpc;
import ystar.living.provider.service.TLivingRoomService;

@DubboService
public class LivingRoomRpcImpl implements ILivingRoomRpc {

    @Resource
    private TLivingRoomService tLivingRoomService;

    @Override
    public PageWrapper<LivingRoomRespDTO> list(LivingRoomReqDTO livingRoomReqDTO) {
        return tLivingRoomService.list(livingRoomReqDTO);
    }

    /**
     * 开播
     * @param livingRoomReqDTO
     * @return
     */
    @Override
    public Integer startLivingRoom(LivingRoomReqDTO livingRoomReqDTO) {
        return tLivingRoomService.startLivingRoom(livingRoomReqDTO);
    }

    /**
     * 关播
     * @param livingRoomReqDTO
     * @return
     */
    @Override
    public boolean closeLiving(LivingRoomReqDTO livingRoomReqDTO) {
        return tLivingRoomService.closeLiving(livingRoomReqDTO);
    }


    @Override
    public LivingRoomRespDTO queryByRoomId(Integer roomId) {
        return tLivingRoomService.queryByRoomId(roomId);
    }
}
