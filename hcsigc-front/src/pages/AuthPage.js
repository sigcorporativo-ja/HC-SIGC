import {Component} from 'react';

import {isLoggedIn, logout} from 'services/AuthService';

class AuthPage extends Component {

  componentWillMount = () => {
    if (!isLoggedIn()) {
      this.props.history.replace('/login');
    }
  };

  handleLogout = () => {
    logout().then(res => this.props.history.replace('/login'));
  };
}

export default AuthPage;
