/*
 * Zed Vulnerability Proxy (ZAP) and its related class files.
 *
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
 *
 * Copyright 2014 The ZAP Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zaproxy.addon.attackprevention.extensionapp;

import javax.swing.JMenuItem;

import org.parosproxy.paros.extension.ExtensionAdaptor;
import org.parosproxy.paros.extension.ExtensionHook;
import org.zaproxy.addon.attackprevention.AttackPreventionProxyListener;

/**
 * An example ZAP extension which adds a top level menu item, a pop up menu item and a status panel.
 *
 * <p>{@link ExtensionAdaptor} classes are the main entry point for adding/loading functionalities
 * provided by the add-ons.
 *
 * @see #hook(ExtensionHook)
 */
public class ExtensionAttackPrevention extends ExtensionAdaptor {

    AttackPreventionProxyListener listener = new AttackPreventionProxyListener();

    // The name is public so that other extensions can access it
    public static final String NAME = "ExtensionAttackPrevention";

    // The i18n prefix, by default the package name - defined in one place to make it easier
    // to copy and change this example
    protected static final String PREFIX = "AttackPrevention";

    /**
     * Relative path (from add-on package) to load add-on resources.
     *
     * @see Class#getResource(String)
     */

    public ExtensionAttackPrevention() {
        super(NAME);
        setI18nPrefix(PREFIX);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void hook(ExtensionHook extensionHook) {
        super.hook(extensionHook);

        extensionHook.addProxyListener(listener);

        // As long as we're not running as a daemon
        if (hasView()) {

            // AttackPrevention tools menu panel.
            JMenuItem attackPreventionActiveEditorMenu =
                    new JMenuItem("Attack Prevention Settings");
            attackPreventionActiveEditorMenu.addActionListener(
                    e -> {
                        AttackPreventionExtensionSettingsUI settingsUI =
                                new AttackPreventionExtensionSettingsUI();
                        settingsUI.setVisible(true);
                    });
            extensionHook.getHookMenu().addToolsMenuItem(attackPreventionActiveEditorMenu);
        }
    }
}
