import { saveAs } from 'file-saver';

export function exportDataAsJSON(data: any) {
    const json = JSON.stringify(data, null, 2);
    const blob = new Blob([json], { type: 'application/json' });
    saveAs(blob, 'data.json');
}

export function exportDataAsXML(data: any) {
    const xml = jsonToXML(data);
    const blob = new Blob([xml], { type: 'application/xml' });
    saveAs(blob, 'data.xml');
}

function jsonToXML(obj: any, rootElement = 'root') {
    let xml = `<${rootElement}>`;
    for (const prop in obj) {
        if (Array.isArray(obj[prop])) {
            xml += `<${prop}>`;
            obj[prop].forEach(item => {
                xml += jsonToXML(item);
            });
            xml += `</${prop}>`;
        } else if (typeof obj[prop] === 'object') {
            xml += jsonToXML(obj[prop], prop);
        } else {
            xml += `<${prop}>${obj[prop]}</${prop}>`;
        }
    }
    xml += `</${rootElement}>`;
    return xml;
}
