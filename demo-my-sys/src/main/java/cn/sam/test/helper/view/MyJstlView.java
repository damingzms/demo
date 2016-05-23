package cn.sam.test.helper.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.JstlView;

public class MyJstlView extends JstlView {
	
	public static final String CONTEXT_PATH = "ctx";

	@Override
	protected void exposeHelpers(HttpServletRequest request) throws Exception {
		String path = request.getContextPath();
		request.setAttribute(CONTEXT_PATH, path);
		super.exposeHelpers(request);
	}
	
}
