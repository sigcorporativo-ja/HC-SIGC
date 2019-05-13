/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import {withStyles} from '@material-ui/core/styles';

import Button from '@material-ui/core/Button'
import Tooltip from '@material-ui/core/Tooltip'
import AddIcon from '@material-ui/icons/Add';
import {secured} from 'utils/Securization';

const styles = theme => ({
  root: {
    marginLeft: '1rem',
    color: '#ffffff'
  },
  lightTooltip: {
    background: theme.palette.common.white,
    color: theme.palette.text.primary,
    boxShadow: theme.shadows[3],
    fontSize: 15
  }
});

class SortSection extends Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const {classes, tooltip} = this.props;
    return (<div>
      <Tooltip title={tooltip} classes={{
          tooltip: classes.lightTooltip
        }}>
        <Button variant="fab" className={classNames(classes.root)} color='primary' onClick={this.props.action}>
          <AddIcon/>
        </Button>
      </Tooltip>
    </div>);
  }
}

SortSection.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default secured()(withStyles(styles, {withTheme: true})(SortSection));
