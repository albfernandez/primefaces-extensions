/**
 * Copyright 2011-2019 PrimeFaces Extensions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.extensions.component.badge;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutcomeTarget;
import javax.faces.context.FacesContext;

import org.primefaces.expression.SearchExpressionFacade;
import org.primefaces.renderkit.CoreRenderer;
import org.primefaces.util.WidgetBuilder;

/**
 * Renderer for the {@link Badge} component.
 *
 * @author Melloware mellowaredev@gmail.com
 * @since 7.1
 */
public class BadgeRenderer extends CoreRenderer {

    @Override
    public void encodeEnd(final FacesContext context, final UIComponent component) throws IOException {
        final Badge badge = (Badge) component;
        encodeScript(context, badge);
    }

    private void encodeScript(FacesContext context, Badge badge) throws IOException {
        String target = SearchExpressionFacade.resolveClientIds(context, badge, badge.getFor());
        if (isValueBlank(target)) {
            target = badge.getParent().getClientId(context);
        }

        final UIComponent targetComponent = SearchExpressionFacade.resolveComponent(context, badge, target);
        if (!(targetComponent instanceof UICommand || targetComponent instanceof UIOutcomeTarget)) {
            throw new FacesException("Badge must use for=\"target\" or be nested inside an button!");
        }

        final WidgetBuilder wb = getWidgetBuilder(context);
        wb.init("ExtBadge", badge.resolveWidgetVar(), badge.getClientId(context));
        wb.attr("target", target);
        wb.attr("color", badge.getColor());
        wb.attr("position", badge.getPosition());
        if (badge.getContent() != null) {
            wb.attr("content", badge.getContent());
        }

        wb.finish();
    }

}
