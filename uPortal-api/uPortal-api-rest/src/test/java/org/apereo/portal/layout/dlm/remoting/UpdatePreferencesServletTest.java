/**
 * Licensed to Apereo under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. Apereo
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at the
 * following location:
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apereo.portal.layout.dlm.remoting;

import java.io.IOException;
import javax.portlet.WindowState;
import org.apereo.portal.IUserIdentityStore;
import org.apereo.portal.UserInstance;
import org.apereo.portal.fragment.subscribe.dao.IUserFragmentSubscriptionDao;
import org.apereo.portal.layout.IStylesheetUserPreferencesService;
import org.apereo.portal.layout.IUserLayoutStore;
import org.apereo.portal.portlet.registry.IPortletDefinitionRegistry;
import org.apereo.portal.portlet.registry.IPortletWindowRegistry;
import org.apereo.portal.security.IPerson;
import org.apereo.portal.security.provider.PersonImpl;
import org.apereo.portal.user.IUserInstance;
import org.apereo.portal.user.IUserInstanceManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class UpdatePreferencesServletTest {
    @InjectMocks UpdatePreferencesServlet updatePreferencesServlet;
    @Mock private IPortletDefinitionRegistry portletDefinitionRegistry;
    @Mock private IUserIdentityStore userIdentityStore;
    @Mock private IUserFragmentSubscriptionDao userFragmentInfoDao;
    @Mock private IUserInstanceManager userInstanceManager;
    @Mock private IStylesheetUserPreferencesService stylesheetUserPreferencesService;
    @Mock private IUserLayoutStore userLayoutStore;
    @Mock private MessageSource messageSource;
    @Mock private IPortletWindowRegistry portletWindowRegistry;
    @Mock private WindowState addedWindowState;
    private MockHttpServletRequest req;
    private MockHttpServletResponse res;

    @Before
    public void setup() {
        updatePreferencesServlet = new UpdatePreferencesServlet();
        res = new MockHttpServletResponse();
        req = new MockHttpServletRequest();
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveElement() throws IOException {
        Mockito.when(userInstanceManager.getUserInstance(req)).thenReturn(null);
        ModelAndView modelAndView = updatePreferencesServlet.removeElement(req, res);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveByFNameNull() throws IOException {
        ModelAndView modelAndView = updatePreferencesServlet.removeByFName(req, res, "fname");
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveByFName() throws IOException {
        IPerson person = new PersonImpl();
        person.setUserName("jDoe");
        person.setFullName("john doe");
        IUserInstance userInstace = new UserInstance(person, null, null);

        Mockito.when(userInstanceManager.getUserInstance(req)).thenReturn(userInstace);
        ModelAndView modelAndView = updatePreferencesServlet.removeByFName(req, res, "fname");
    }

    @Test(expected = NullPointerException.class)
    public void testSubscribeToTab() throws IOException {
        ModelAndView modelAndView = updatePreferencesServlet.subscribeToTab(req, res);
    }

    @Test(expected = NullPointerException.class)
    public void testMovePortletAjax() {
        req.setLocalName("en-US");
        ModelAndView modelAndView =
                updatePreferencesServlet.movePortletAjax(
                        req, res, "sourceId", "prevNodeIs", "nextNodeId");
    }

    @Test(expected = NullPointerException.class)
    public void testMoveElement() throws IOException {
        req.setLocalName("en-US");
        Mockito.when(userInstanceManager.getUserInstance(req)).thenReturn(null);
        ModelAndView modelAndView =
                updatePreferencesServlet.moveElement(req, res, "sourceId", "get", "elementId");
    }
}
