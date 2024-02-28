import { Form, InputGroup, OverlayTrigger, Tooltip } from 'react-bootstrap';
import get from 'lodash.get';
import { useRecoilState, useRecoilValue } from 'recoil';
import {
  bufferRoute,
  feedbackRouteValidator,
  isAddingWithLocationIds,
  wasValidated
} from '../state/atoms';
import set from 'lodash.set';
import { validate } from '../utils/routeValidator';

export const InputField = ({ id, isEmbedded = false, type = 'number', step = 'any' }) => {
  const [route, setRoute] = useRecoilState(bufferRoute);
  const [feedback, setFeedback] = useRecoilState(feedbackRouteValidator);
  const validated = useRecoilValue(wasValidated);
  const isWithLocationIds = useRecoilValue(isAddingWithLocationIds);

  const change = (event) => {
    const newRoute = JSON.parse(JSON.stringify(route));
    set(newRoute, event.target.id, event.target.value);
    setRoute(newRoute);
    setFeedback(validate(newRoute, isWithLocationIds));
  };

  return isEmbedded ? (
    <>
      <InputGroup.Text id={id}>{id.split('.').pop()}</InputGroup.Text>
      <OverlayTrigger
        placement="bottom"
        overlay={<Tooltip id={'tooltip' + id}>{validated ? get(feedback, id, '') : ''}</Tooltip>}>
        <Form.Control
          id={id}
          value={get(route, id, '')}
          type={type}
          step={step}
          onChange={change}
          isInvalid={get(feedback, id, '') !== '' && validated}
          isValid={get(feedback, id, '') === '' && validated}
        />
      </OverlayTrigger>
    </>
  ) : (
    <OverlayTrigger
      placement="bottom"
      overlay={<Tooltip id={'tooltip' + id}>{validated ? get(feedback, id, '') : ''}</Tooltip>}>
      <Form.Group className="mb-3">
        <Form.Label htmlFor={id}>{id.firstLetterToUppercase()}</Form.Label>
        <Form.Control
          id={id}
          value={get(route, id, '')}
          type={type}
          step={step}
          onChange={change}
          isInvalid={get(feedback, id, '') !== '' && validated}
          isValid={get(feedback, id, '') === '' && validated}
        />
      </Form.Group>
    </OverlayTrigger>
  );
};
