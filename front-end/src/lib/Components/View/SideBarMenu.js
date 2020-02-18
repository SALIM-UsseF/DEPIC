import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import { 
  Icon,
  Menu, 
  Sidebar 
} from 'semantic-ui-react'

import Title from '../Title'

import { dictionnary } from '../../Langs/langs'

export default class SideBarMenu extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    open: PropTypes.bool,
    title: PropTypes.string,
    onHide: PropTypes.func,
    onItemClick: PropTypes.func,
    onOpenSondage: PropTypes.func
  }

  static defaultProps = {
    lang: 'fr'
  }

  onHide = () => {
    if (this.props.onHide) {
      this.props.onHide();
    }
  }

  handleItemClick = (e, {name}) => {
    if (this.props.onItemClick) {
      this.props.onItemClick(name);
    }

    if (this.props.onHide) {
      this.props.onHide();
    }

    if (name === 'createSurvey') {
      if (this.props.onOpenSondage) {
        this.props.onOpenSondage();
      }
    }
  }

  render() {
    let lang = _.toUpper(this.props.lang);
    let dashboard = _.get(dictionnary, lang + '.dashboard');
    let resultats = _.get(dictionnary, lang + '.resultats');
    let createSurvey = _.get(dictionnary, lang + '.createSurvey');
    let backgroundColorMenuItem = 'lightgrey';

    return (
      <React.Fragment>
        <Sidebar
          as={Menu}
          animation='overlay'
          vertical
          visible={this.props.open}
          icon='labeled'
          style={{ width:'250px' }}
          onHide={this.onHide}
          size='massive'
        >
          <Menu.Item 
            header 
            as='h1' 
          />
          <Menu.Item
            name='dashboard'
            style={{
              backgroundColor: (this.props.title === 'dashboard')?backgroundColorMenuItem:''
            }}
            onClick={this.handleItemClick}
          >
            <Icon name='dashboard' />
            <Title
              as='h3'
              content={_.upperFirst(dashboard)} />
          </Menu.Item>
          <Menu.Item
            name='createSurvey'
            style={{
              backgroundColor: (this.props.title === 'createSurvey')?backgroundColorMenuItem:''
            }}
            onClick={this.handleItemClick}
          >
            <Icon name='numbered list' />
            <Title
              as='h3'
              content={_.upperFirst(createSurvey)} />
          </Menu.Item>
          <Menu.Item
            disabled
            name='resultats'
            style={{
              backgroundColor: (this.props.title === 'resultats')?backgroundColorMenuItem:''
            }}
            onClick={this.handleItemClick}
          >
            <Icon name='chart line' />
            <Title
              as='h3'
              content={_.upperFirst(resultats)} />
          </Menu.Item>
        </Sidebar>
      </React.Fragment>
    );
  }
}