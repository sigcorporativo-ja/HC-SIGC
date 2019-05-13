import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';

import TableHC from 'components/TableHC';

const styles = theme => ({});

class ViewUsers extends Component {
  constructor(props) {
    super(props);
    this.state = {
      rows: []
    };
  }

  componentDidMount() {
    const {model: {
        users
      }} = this.props;

    this.setState({
      rows: users.map(user => [user.username, user.email])
    });
  }

  render() {
    const {t} = this.props;
    const {rows} = this.state;
    return (<TableHC header={[t('username'), t('mail')]} rows={rows}/>);
  }
}

ViewUsers.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(ViewUsers));
