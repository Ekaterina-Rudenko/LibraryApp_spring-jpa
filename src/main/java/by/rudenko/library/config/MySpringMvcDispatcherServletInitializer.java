package by.rudenko.library.config;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MySpringMvcDispatcherServletInitializer extends
    AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return null;
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[]{SpringConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[]{"/"};
  }

  public void onStartup(ServletContext aServletContext) throws ServletException {
    super.onStartup(aServletContext);
    registerCharacterEncodingFilter(aServletContext);
    registerHiddenFieldFilter(aServletContext);
  }

  private void registerCharacterEncodingFilter(ServletContext aContext) {
    EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding("UTF-8");
    characterEncodingFilter.setForceEncoding(true);

    FilterRegistration.Dynamic characterEncoding = aContext.addFilter("characterEncoding", characterEncodingFilter);
    characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
  }

  private void registerHiddenFieldFilter(ServletContext aServletContext){
    aServletContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter())
        .addMappingForUrlPatterns(null, true,"/*" );
  }
}
