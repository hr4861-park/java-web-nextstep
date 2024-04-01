package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;

public class DeleteQuestionController extends AbstractController {
    QuestionDao questionDao = QuestionDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        final Question byId = questionDao.findById(questionId);
        if (byId == null) {
            return jspView("show.jsp").addObject("question", qnaService.findById(questionId))
                .addObject("answers", questionDao.findAllByQuestionId(questionId))
                .addObject("errorMessage", e.getMessage());
        }
        try {

            questionDao.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(req.getSession()));
            return jspView("redirect:/");
        } catch (CannotDeleteException e) {
            return jspView("show.jsp").addObject("question", qnaService.findById(questionId))
                .addObject("answers", qnaService.findAllByQuestionId(questionId))
                .addObject("errorMessage", e.getMessage());
        }
    }
}