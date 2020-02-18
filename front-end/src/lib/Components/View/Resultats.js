import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'
import { Divider, Segment, List } from 'semantic-ui-react'

const styles = {
  titre: {
    fontFamily: 'serif',
    fontSize: '1.2em'
  },
  normal: {
    fontFamily: 'serif',
    fontSize: 'smaller'
  }
};

export default class Resultats extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    idSondage: PropTypes.number
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    resultats: {}
  }

  componentDidMount() {
    this.props.client.Resultat.read(
      this.props.idSondage,
      result => {
        this.setState({
          resultats: result.data
        });
      },
      error => {
        console.log(error);
      }
    )
  }

  render() {
    let key = 0;

    return (
      <React.Fragment>
        <Divider hidden /> 
        <List divided relaxed>
          {_.map(this.state.resultats, resultat => {
            key++;
            
            return (
              <Segment color='teal' key={key}>
                <p style={styles.titre}>Nombre de participations : {resultat.nombre_de_participations}</p>
                <p style={styles.titre}>Nombre de questions : {resultat.nombre_de_questions}</p>
                <p style={styles.titre}>Moyennes générales des questions à points : </p>
                <p style={styles.titre}>Nombre de participations sur chaque choix des questions à choix unique</p>
                <p style={styles.titre}>Nombre de participations sur chaque choix des questions à choix multiple</p>
              </Segment>
            )
          })}
        </List>
      </React.Fragment>
    );
  }
}