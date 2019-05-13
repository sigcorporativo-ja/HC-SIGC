import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route, Switch, Redirect} from 'react-router-dom';
import axios from 'axios';

import 'styles/index.css';
import 'styles/fonts.css';

import {I18nextProvider} from 'react-i18next';
import i18n from 'components/i18n';
import ScrollToTop from 'components/ScrollToTop';

import {MuiThemeProvider} from '@material-ui/core/styles';
import theme from 'styles/palette';

import registerServiceWorker from './registerServiceWorker';

import Main from 'pages/Main';
import Datasets from 'pages/Datasets';
import OrganizationUnits from 'pages/OrganizationUnits';
import Users from 'pages/Users';
import Groups from 'pages/Groups';
import Configuration from 'pages/Configuration';
import DBMS from 'pages/DBMS';
import DBConnection from 'pages/DBConnection';

// Enable axios for using JWT
axios.defaults.withCredentials = true;
axios.defaults.headers.common['Access-Control-Allow-Credentials'] = true;
axios.defaults.headers.common.Accept = 'application/json';
axios.defaults.headers.common['Content-Type'] = 'application/json';

ReactDOM.render(<I18nextProvider i18n={i18n}>
  <Router basename="/hcsigc">
    <ScrollToTop>
      <MuiThemeProvider theme={theme}>
        <Switch>
          <Redirect exact={true} from='/' to='/main'/>
          <Route path="/main" exact={true} component={Main}/>
          <Route path="/datasets" exact={true} component={Datasets}/>
          <Route path="/ou" exact={true} component={OrganizationUnits}/>
          <Route path="/users" exact={true} component={Users}/>
          <Route path="/groups" exact={true} component={Groups}/>
          <Route path="/configuration" exact={true} component={Configuration}/>
          <Route path="/dbms" exact={true} component={DBMS}/>
          <Route path="/dbconnection" exact={true} component={DBConnection}/>
        </Switch>
      </MuiThemeProvider>
    </ScrollToTop>
  </Router>
</I18nextProvider>, document.getElementById('root'));
registerServiceWorker();
