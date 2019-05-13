import React, {Component} from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';

import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import Save from '@material-ui/icons/Save';
import Cancel from '@material-ui/icons/Cancel';
import CloseIcon from '@material-ui/icons/Close';
import Divider from '@material-ui/core/Divider';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import AppBar from '@material-ui/core/AppBar';

const styles = theme => ({
  btn: {
    display: 'flex',
    flexDirection: 'row'
  },
  icon: {
    marginLeft: '5px'
  },
  title: {
    backgroundColor: '#1C74A6',
    marginTop: '-30px'
  },
  dialogContent: {
    justifyContent: 'space-around'
  },
  typography: {
    color: '#ffffff',
    fontSize: '1.5rem'
  },
  close: {
    display: 'flex',
    justifyContent: 'flex-end',
    backgroundColor: '#1C74A6',
    width: "auto",
    height: "auto"
  },
  tabsContainer: {
    flexGrow: 1,
    backgroundColor: '#000'
  },
  tabBar: {
    backgroundColor: '#fff',
    color: theme.palette.primary.main
  },
  containerContent: {
    height: '30vh'
  },
  appbar: {
    boxShadow: '0 0 0 0',
    borderBottom: '1px solid ' + theme.palette.primary.main
  }
});

const wrapComponent = (wrappedComponent, classes) => {
  return (<div className={classes.containerContent}>
    {wrappedComponent}
  </div>);
}

class ModalDialog extends Component {

  constructor(props) {
    super(props);
    this.state = {
      value: 0
    };
  }

  handleChange = (event, value) => {
    this.setState({value});
  };

  render() {
    const {
      classes,
      t,
      saveAction,
      closeDialog,
      openModal,
      title,
      textButton,
      disableActionButtons,
      childrenComponents,
      children,
      inheritedClasses
    } = this.props;
    const {value} = this.state;

    const child = childrenComponents === undefined
      ? children
      : childrenComponents[value].component;

    const tabs = childrenComponents === undefined || childrenComponents.length < 2
      ? null
      : childrenComponents.map(childObj => {
        return (<Tab key={childObj.label} label={childObj.label}/>);
      });

    const containerTabs = childrenComponents === undefined || childrenComponents.length < 2
      ? null
      : (<div className={classes.tabsContainer}>
        <AppBar className={classNames(classes.appbar)} position="static">
          <Tabs value={value} onChange={this.handleChange}>
            {tabs}
          </Tabs>
        </AppBar>
      </div>);

    const actionButtons = disableActionButtons === true
      ? null
      : (<DialogActions className={classNames(classes.dialogContent)}>
        <Button variant="contained" className={classNames(classes.btn)} onClick={closeDialog} color="primary">
          {t('cancel')}
          <Cancel className={classNames(classes.icon)}/>
        </Button>
        <Button variant="contained" className={classNames(classes.btn)} onClick={saveAction} color="primary">
          {textButton}
          <Save className={classNames(classes.icon)}/>
        </Button>
      </DialogActions>)

    return (<div className={classes.root}>
      <Dialog open={openModal} onClose={closeDialog} caria-labelledby="form-dialog-title" disableRestoreFocus={true}>
        <div className={classes.close}>
          <IconButton onClick={closeDialog} color={'secondary'} className={classNames(classes.closeBtn)}>
            <CloseIcon/>
          </IconButton>
        </div>
        <DialogTitle className={classNames(classes.title)} color={'primary'} id="form-dialog-title">
          <Typography className={classNames(classes.typography)}>{title}</Typography>
        </DialogTitle>
        <Divider/> {containerTabs}
        <DialogContent>
          {wrapComponent(child, inheritedClasses || classes)}
        </DialogContent>
        <Divider/> {actionButtons}
      </Dialog>
    </div>);
  }
}

ModalDialog.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(ModalDialog));
