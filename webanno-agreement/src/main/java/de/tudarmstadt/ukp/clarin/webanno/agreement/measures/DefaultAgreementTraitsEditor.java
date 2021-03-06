/*
 * Copyright 2019
 * Ubiquitous Knowledge Processing (UKP) Lab and FG Language Technology
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.tudarmstadt.ukp.clarin.webanno.agreement.measures;

import static de.tudarmstadt.ukp.clarin.webanno.support.lambda.LambdaBehavior.visibleWhen;
import static java.util.Arrays.asList;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.select.BootstrapSelect;
import de.tudarmstadt.ukp.clarin.webanno.curation.casdiff.LinkCompareBehavior;
import de.tudarmstadt.ukp.clarin.webanno.model.AnnotationFeature;
import de.tudarmstadt.ukp.clarin.webanno.model.LinkMode;

public class DefaultAgreementTraitsEditor<T extends DefaultAgreementTraits>
    extends Panel
{
    private static final long serialVersionUID = 7780019891761754494L;

    private final DropDownChoice<LinkCompareBehavior> linkCompareBehaviorDropDown;

    private final Form<T> form;
    
    public DefaultAgreementTraitsEditor(String aId, IModel<AnnotationFeature> aFeature,
            IModel<T> aModel)
    {
        super(aId, aModel);
        
        form = new Form<T>("form", CompoundPropertyModel.of(aModel)) {
            private static final long serialVersionUID = -1422265935439298212L;

            @Override
            protected void onSubmit()
            {
                // TODO Auto-generated method stub
                super.onSubmit();
            }
        };
        
        linkCompareBehaviorDropDown = new BootstrapSelect<>("linkCompareBehavior",
                asList(LinkCompareBehavior.values()),
                new EnumChoiceRenderer<>(this));
        linkCompareBehaviorDropDown.add(visibleWhen(() -> 
                aFeature.map(f -> !LinkMode.NONE.equals(f.getLinkMode()))
                .orElse(false).getObject()));
        linkCompareBehaviorDropDown.setOutputMarkupPlaceholderTag(true);
        form.add(linkCompareBehaviorDropDown);

        form.add(new CheckBox("limitToFinishedDocuments"));
        
        add(form);
    }
    
    public T getModelObject()
    {
        return (T) getDefaultModelObject();
    }
    
    protected Form<T> getForm()
    {
        return form;
    }
}
