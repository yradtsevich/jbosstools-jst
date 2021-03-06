/******************************************************************************* 
 * Copyright (c) 2013 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.tools.jst.web.ui.palette.html.jquery.wizard;

import java.beans.PropertyChangeEvent;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.jboss.tools.common.ui.widget.editor.IFieldEditor;
import org.jboss.tools.jst.web.ui.palette.html.wizard.WizardMessages;

/**
 * 
 * @author Viacheslav Kabanovich
 *
 */
public class NewFooterWizardPage extends NewJQueryWidgetWizardPage {
	ButtonsEditor buttons = new ButtonsEditor(this, 0, 4);

	public NewFooterWizardPage() {
		super("newFooter", WizardMessages.newFooterWizardTitle);
		setDescription(WizardMessages.newFooterWizardDescription);
	}

	protected void createFieldPanel(Composite parent) {
		buttons.setLabel(0, "Add");
		buttons.setLabel(1, "Up");
		buttons.setLabel(2, "Down");
		buttons.setLabel(3, "Remove");
		buttons.setIcon(0, "plus");
		buttons.setIcon(1, "arrow-u");
		buttons.setIcon(2, "arrow-d");
		buttons.setIcon(3, "delete");
		IFieldEditor title = JQueryFieldEditorFactory.createTitleEditor();
		title.setValue("");
		addEditor(title, parent);

		createIDEditor(parent, true);

		TwoColumns columns = createTwoColumns(parent);

		IFieldEditor fixed = JQueryFieldEditorFactory.createFixedPositionEditor();
		addEditor(fixed, columns.left());
		
		IFieldEditor fullScreen = JQueryFieldEditorFactory.createFullScreenEditor();
		addEditor(fullScreen, columns.right());

		setEnabled(EDITOR_ID_FULL_SCREEN, false);

		Composite panel = buttons.createControl(parent, "Buttons");

		IFieldEditor arrangement = JQueryFieldEditorFactory.createArragementEditor();
		addEditor(arrangement, panel);

		columns = createTwoColumns(panel);
		GridLayout l = (GridLayout)columns.left().getLayout();
		l.marginBottom = 2;
		columns.left().setLayout(l);

		IFieldEditor iconpos = JQueryFieldEditorFactory.createIconPositionEditor();
		addEditor(iconpos, columns.left(), true);

		IFieldEditor icononly = JQueryFieldEditorFactory.createIconOnlyEditor();
		addEditor(icononly, columns.right());

		IFieldEditor theme = JQueryFieldEditorFactory.createDataThemeEditor();
		addEditor(theme, parent, true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(buttons.isSwitching) {
			return;
		}
		String name = evt.getPropertyName();
		String value = evt.getNewValue().toString();
		buttons.onPropertyChange(name, value);

		boolean hasIcons = buttons.hasIcons();
		boolean icononly = isTrue(EDITOR_ID_ICON_ONLY);
		setEnabled(EDITOR_ID_ICON_POS, hasIcons && !icononly);
		setEnabled(EDITOR_ID_ICON_ONLY, hasIcons);

		boolean isFixed = TRUE.equals(getEditorValue(EDITOR_ID_FIXED_POSITION));
		setEnabled(EDITOR_ID_FULL_SCREEN, isFixed);
		
		super.propertyChange(evt);
	}

}
