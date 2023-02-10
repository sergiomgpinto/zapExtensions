/*
 * Zed Attack Proxy (ZAP) and its related class files.
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
package org.zaproxy.addon.profilingproxy.extensionapp;

import org.parosproxy.paros.extension.ExtensionAdaptor;
import org.parosproxy.paros.extension.ExtensionHook;
import org.zaproxy.addon.profilingproxy.extensionapp.ui.ProfilingProxyStatusPanel;

/**
 * Profiling Proxy extension allows to identify potential performance improvements.
 *
 * <p>{@link ExtensionAdaptor} classes are the main entry point for adding/loading functionalities
 * provided by the add-ons.
 *
 * @see #hook(ExtensionHook)
 */
public class ExtensionProfilingProxy extends ExtensionAdaptor {

    public static final String NAME = "ExtensionProfilingProxy";
    protected static final String PREFIX = "profilingProxy";
    private ProfilingProxyStatusPanel statusPanel;
    private final ProfilingProxyListener listener;

    public ExtensionProfilingProxy() {
        super(NAME);
        setI18nPrefix(PREFIX);
        statusPanel = getStatusPanel();
        listener = new ProfilingProxyListener(statusPanel);
    }

    /**
     * Allows to attach the status panel for this extension to the main UI.
     * @param extensionHook the hook to add the components.
     */
    @Override
    public void hook(ExtensionHook extensionHook) {
        super.hook(extensionHook);

        extensionHook.addProxyListener(listener);
        // As long as we're not running as a daemon
        if (hasView()) {
            extensionHook.getHookView().addStatusPanel(statusPanel);
       }
    }

    /**
     * @return the status panel for this extension.
     */
    private ProfilingProxyStatusPanel getStatusPanel() {
        if (statusPanel == null) {
            statusPanel = new ProfilingProxyStatusPanel();
        }
        return statusPanel;
    }
}
