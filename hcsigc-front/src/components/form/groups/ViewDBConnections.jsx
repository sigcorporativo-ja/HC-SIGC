import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';

import TableHC from 'components/TableHC';

const styles = theme => ({});

class ViewDBConnections extends Component {
  constructor(props) {
    super(props);
    this.state = {
      rows: []
    };
  }

  componentDidMount() {
    const {model: {
        dbconnections
      }} = this.props;

    this.setState({
      rows: dbconnections.map(dbcon => [dbcon.name, dbcon.host])
    });
  }

  render() {
    const {t} = this.props;
    const {rows} = this.state;
    return (<TableHC header={[t('descriptive_name'), t('host')]} rows={rows}/>);
  }
}

ViewDBConnections.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(ViewDBConnections));
