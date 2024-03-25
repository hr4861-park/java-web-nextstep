package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.dao.QuestionDao;

public class QnaListController extends AbstractController {

    private QuestionDao questionDao = new QuestionDao();
    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        return jsonView().addObject("questions", questionDao.findAll());
    }
}
