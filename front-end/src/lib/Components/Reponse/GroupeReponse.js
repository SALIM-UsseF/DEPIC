import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import ReponseChoix from './ReponseChoix'
import ReponseOuverte from './ReponseOuverte'
import ReponsePoints from './ReponsePoints'

import { 
  Segment 
} from 'semantic-ui-react'

//import { dictionnary } from '../../Langs/langs'

const styles = {
  titleQuestion: {
    fontSize: "20px",
    marginTop: "25px",
    marginBottom: "25px"
  }
};

export default class GroupeReponse extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    obligatoire: PropTypes.bool,
    idQuestion: PropTypes.number,
    intitule: PropTypes.string,
    ordre: PropTypes.number,
    styles: PropTypes.object
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    questions: []
  }

  componentDidMount() {
    this.props.client.Groupe.read(
      this.props.idQuestion,
      result => {
        this.setState({
          questions: result.data
        });
      },
      error => {
        console.log(error);
      }
    );
  }

  render() {
    let key = 0;

    return (
      <React.Fragment>
        <p 
          style={this.props.styles.titleQuestion}
        >{this.props.ordre}. {this.props.intitule}{this.props.obligatoire?' * ':''}</p>

        <Segment color='teal'>
          {_.map(this.state.questions, question => {
            key++;

            if (question.type === 'QuestionChoix') {
              return (
                <ReponseChoix
                  key={key} 
                  client={this.props.client}
                  obligatoire={question.estObligatoire}
                  unique={question.estUnique}
                  idQuestion={question.id_question}
                  intitule={question.intitule}
                  nbChoix={question.nombreChoix}
                  ordre={key}
                  styles={styles} />
              );
            } else if (question.type === 'QuestionOuverte') {
              return (
                <ReponseOuverte
                  key={key}
                  client={this.props.client}
                  obligatoire={question.estObligatoire}
                  idQuestion={question.id_question}
                  intitule={question.intitule}
                  nombreDeCaractere={question.nombreDeCaractere}
                  ordre={key}
                  styles={styles} />
              );
            } else if (question.type === 'QuestionPoint') {
              return (
                <ReponsePoints
                  key={key}
                  client={this.props.client}
                  obligatoire={question.estObligatoire}
                  idQuestion={question.id_question}
                  intitule={question.intitule}
                  maxPoints={question.maxPoints}
                  minPoints={question.minPoints}
                  ordre={key}
                  styles={styles} />
              );
            }
          })}
        </Segment>      
      </React.Fragment>
    );
  }
}