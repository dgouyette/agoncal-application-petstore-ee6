package org.agoncal.application.petstore.util;


import fj.data.Option;
import org.apache.commons.lang3.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Option.class, value = "optionalConverter")
public class OptionalConverter implements Converter {


    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (StringUtils.isNotBlank(s)) {
            return Option.fromNull(s);
        }
        return Option.none();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o instanceof Option) {
            Option optional = (Option) o;
            if (optional.isSome()) {
                return String.valueOf(optional.some());
            } else {
                return "";
            }

        }
        return "";
    }
}
