import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import SideBarMenu from './SideBarMenu'
import SideBarHome from './SideBarHome'
import Frame from './Frame'

import { dictionnary } from '../../Langs/langs'

const styles = {
  frame: {
    paddingTop: "100px",
    paddingLeft: "100px",
    paddingRight: "100px",
    margin: "0 auto",
    maxWidth: "1500px",
    display: "flex",
    flexDirection: "column",
    width: "Auto"
  }
};

export default class View extends React.Component {
  static propTypes = {
    lang: PropTypes.string
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    openSideBar: false,
    title: 'dashboard'
  }

  onItemClickSideBarHome = name => {
    if (name === 'home') {
      this.setState({
        openSideBar: true
      });
    } else if (name === 'user') {
      console.log('OK')
    }
  }

  onHideSideBar = () => {
    this.setState({
      openSideBar: false
    });
  }

  onItemClickSideBarMenu = name => {
    this.setState({
      title: name
    });
  }

  render() {
    let lang = _.toUpper(this.props.lang);
    let title = _.get(dictionnary, lang + '.' + this.state.title);

    return (
      <React.Fragment>
        {/* SideBarHome : Barre de menu d'accueil */}
        <SideBarHome 
          onItemClick={this.onItemClickSideBarHome}
        />

        <div style={styles.frame}>
          <Frame
            lang={this.props.lang}
            title={_.upperFirst(title)} />
        </div>

        {/* SideBarMenu : Barre de menu de paramètres */}
        <SideBarMenu
          lang={this.props.lang}
          open={this.state.openSideBar}
          onHide={this.onHideSideBar}
          onItemClick={this.onItemClickSideBarMenu}
        />
      </React.Fragment>
    );
  }
}