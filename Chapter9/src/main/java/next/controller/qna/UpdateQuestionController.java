package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

public class UpdateQuestionController extends AbstractController {

    private QuestionDao questionDao = QuestionDao.getInstance();

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(request.getParameter("questionId"));
        Question question = questionDao.findById(questionId);
        final User user = UserSessionUtils.getUserFromSession(request.getSession());

        if (user.getUserId().equals(question.getWriter())) {
            throw new IllegalArgumentException("Can not update");
        }

        Question newQuestion = new Question(question.getWriter(), request.getParameter("title"),
            request.getParameter("contents"));
        question.setTitle(newQuestion.getTitle());
        question.setContents(newQuestion.getContents());
        questionDao.update(question);
        return jspView("redirect:/");
    }
}
