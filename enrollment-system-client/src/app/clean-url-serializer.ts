import {DefaultUrlSerializer, UrlTree} from '@angular/router';

export let appContants = {
  outlets: ['panel']
};

export class CleanUrlSerializer extends DefaultUrlSerializer {
  // tslint:disable-next-line:variable-name
  private _defaultUrlSerializer: DefaultUrlSerializer = new DefaultUrlSerializer();

  parse(url: string): UrlTree {
    appContants.outlets.forEach(outletName => {
      const reg = new RegExp('dashboard/([^.]*)');
      url = url.replace(reg, 'dashboard/(' + outletName + ':$1)' );
    });
    return this._defaultUrlSerializer.parse(url);
  }

  serialize(tree: UrlTree): string {
    let url = this._defaultUrlSerializer.serialize(tree);
    appContants.outlets.forEach(outletName => {
      const reg = new RegExp('\\(' + outletName + ':([^.]*)\\)');
      url = url.replace(reg, '$1');
    });
    return url;
  }
}
