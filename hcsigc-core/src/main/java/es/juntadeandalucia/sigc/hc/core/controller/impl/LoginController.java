/* Copyright 2015 Guadaltel, S.A.
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */
package es.juntadeandalucia.sigc.hc.core.controller.impl;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.picketlink.annotations.PicketLink;
import org.picketlink.authentication.BaseAuthenticator;
import org.picketlink.credential.DefaultLoginCredentials;

import es.juntadeandalucia.sigc.hc.core.bo.UserBO;

@PicketLink
@Transactional
public class LoginController extends BaseAuthenticator implements Serializable {

   private static final long serialVersionUID = 8151017257796599175L;

   private static final Log LOG = LogFactory.getLog(LoginController.class);

   @Inject
   private DefaultLoginCredentials credentials;

   @Inject
   private UserController userController;

   @Override
   public void authenticate() {
      String username = credentials.getUserId();
      String password = credentials.getPassword();

      UserBO user = userController.findByNameAndPassword(username, password);
      if (user != null) {
         setStatus(AuthenticationStatus.SUCCESS);
         setAccount(user);
         LOG.info("User authenticate SUCCESS.");
      }
      else {
         LOG.error("User authenticate FAILURE.");
         setStatus(AuthenticationStatus.FAILURE);
      }
   }
}
