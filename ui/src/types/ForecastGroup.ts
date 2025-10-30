import type { EntryReference } from "./Entry";
import type { SpanReference } from "./Span";

export interface ForecastGroup {
    uuid?: string,
    groupName?: string,
    gasPricePerKwh?: number,
    kwhFactorPerQubicmeter?: number,
    entries?: EntryReference[]
    spans?: SpanReference[]
}