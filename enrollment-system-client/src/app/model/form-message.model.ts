import {TypeMessage} from './enumeration/type-message.enum';

export class FormMessage {
  type: TypeMessage;
  message?: string;

  constructor() {
    this.type = TypeMessage.NONE;
  }
}
