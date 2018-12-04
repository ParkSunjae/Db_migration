package kr.co.kyobo.vora.service.help;

import kr.co.kyobo.vora.messages.ErrorMessages;
import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.Help;
import kr.co.kyobo.vora.model.vo.HelpListObj;
import kr.co.kyobo.vora.model.vo.HelpRequest;
import kr.co.kyobo.vora.repository.HelpRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class HelpServiceImpl implements HelpService {

    @Autowired
    HelpRepository helpRepository;

    @Override
    public ResponseEntity<Object> getHelpListAll(HelpRequest helpRequest, HttpServletResponse response) {
        helpRequest.setTotal(helpRepository.countAll());
        helpRequest = setPage(helpRequest);

        List<HelpListObj> helpList = helpRepository.findAll(helpRequest);
        helpRequest.setHelpList(helpList);
        return ResponseEntityUtil.makeResultSuccess(helpRequest);
    }

    @Override
    public ResponseEntity<Object> getHelpListByUseYn(HelpRequest helpRequest, HttpServletResponse response) {
        helpRequest.setTotal(helpRepository.countByUserYN(helpRequest));
        helpRequest = setPage(helpRequest);

        List<HelpListObj> helpList = helpRepository.findByUseYn(helpRequest);
        helpRequest.setHelpList(helpList);
        return ResponseEntityUtil.makeResultSuccess(helpRequest);
    }

    @Override
    public ResponseEntity<Object> insertHelp(Help help, HttpServletResponse response) {
        int rst = helpRepository.insert(help);
        if(rst>0){
            Map<String,Object> result = new HashMap<>();
            result.put("data",help);
            return ResponseEntityUtil.makeResultSuccess(result);
        }else{
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_INSERT_FAIL.getCode());
        }
    }

    @Override
    public ResponseEntity<Object> updateHelp(Help help, HttpServletResponse response) {
        int rst = helpRepository.update(help);
        if(rst>0){
            Map<String,Object> result = new HashMap<>();
            result.put("data",help);
            return ResponseEntityUtil.makeResultSuccess(result);
        }else{
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_UPDATE_FAIL.getCode());
        }
    }

    @Override
    public ResponseEntity<Object> deleteHelp(Help help, HttpServletResponse response) {
        int result = helpRepository.deleteByIdx(help);
        if(result>0){
            return ResponseEntityUtil.makeResultSuccess(result);
        }else{
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_DELETE_FAIL.getCode());
        }
    }

    private HelpRequest setPage(HelpRequest helpRequest) {
        if(helpRequest.getPage() > 1){
            if(helpRequest.getTotal() < helpRequest.getPage() * helpRequest.getLimit()){
                helpRequest.setPage(
                        helpRequest.getTotal()/helpRequest.getLimit()
                                + (helpRequest.getTotal()%helpRequest.getLimit()>0?1:0)
                );
            }
            helpRequest.setOffset((helpRequest.getPage() - 1) * helpRequest.getLimit());
        }else{
            helpRequest.setOffset((helpRequest.getPage() - 1));
        }
        return helpRequest;
    }


}
