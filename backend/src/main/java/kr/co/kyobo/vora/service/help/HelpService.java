package kr.co.kyobo.vora.service.help;

import kr.co.kyobo.vora.model.database.Help;
import kr.co.kyobo.vora.model.vo.HelpRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface HelpService {
    ResponseEntity<Object> getHelpListByUseYn(HelpRequest helpRequest, HttpServletResponse response);

    ResponseEntity<Object> getHelpListAll(HelpRequest helpRequest, HttpServletResponse response);

    ResponseEntity<Object> updateHelp(Help help, HttpServletResponse response);

    ResponseEntity<Object> insertHelp(Help help, HttpServletResponse response);

    ResponseEntity<Object> deleteHelp(Help help, HttpServletResponse response);
}
